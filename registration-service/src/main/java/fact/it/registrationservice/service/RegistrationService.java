package fact.it.registrationservice.service;

import fact.it.registrationservice.dto.EventResponse;
import fact.it.registrationservice.dto.RegistrationRequest;
import fact.it.registrationservice.dto.RegistrationResponse;
import fact.it.registrationservice.dto.TicketResponse;
import fact.it.registrationservice.model.Registration;
import fact.it.registrationservice.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final WebClient webClient;

    public boolean createRegistration(RegistrationRequest registrationRequest) {
        Registration registration = new Registration();
        registration.setRegistrationCode(UUID.randomUUID().toString());
        registration.setFirstName(registrationRequest.getFirstName());
        registration.setLastName(registrationRequest.getLastName());
        registration.setEmail(registrationRequest.getEmail());
        registration.setAmount(registrationRequest.getAmount());

        // Retrieve the ticket code and event code from the request
        String ticketCode = registrationRequest.getTicketCode();
        String eventCode = registrationRequest.getEventCode();

        // Fetch the ticket information
        TicketResponse ticketResponse = webClient.get()
                .uri("http://localhost:8081/api/ticket/code/{ticketCode}", ticketCode)
                .retrieve()
                .bodyToMono(TicketResponse.class)
                .block();

        // Fetch the event information
        EventResponse eventResponse = webClient.get()
                .uri("http://localhost:8080/api/event/code/{eventCode}", eventCode)
                .retrieve()
                .bodyToMono(EventResponse.class)
                .block();

        boolean ticketAvailable = ticketResponse != null && ticketResponse.getAmountLeft() > 0;
        boolean eventExists = eventResponse != null;

        if (ticketAvailable && eventExists) {
            // Set the ticket and event codes in the registration object
            registration.setTicketCode(ticketCode);
            registration.setEventCode(eventCode);

            // Save the registration to the database
            registrationRepository.save(registration);
            return true;
        } else {
            return false; // Handle cases where the ticket is not available or the event does not exist
        }
    }

    public Optional<RegistrationResponse> getRegistrationByRegistrationCode(String registrationCode) {
        return registrationRepository.findByRegistrationCode(registrationCode)
                .map(this::mapToRegistrationResponse);
    }

    public List<RegistrationResponse> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream().map(this::mapToRegistrationResponse).toList();
    }

    public void updateRegistration(Integer id, RegistrationRequest updatedRequest) {
        Optional<Registration> registrationOpt = registrationRepository.findById(String.valueOf(id));
        if (registrationOpt.isPresent()) {
            Registration registration = registrationOpt.get();
            registration.setFirstName(updatedRequest.getFirstName());
            registration.setLastName(updatedRequest.getLastName());
            registration.setEmail(updatedRequest.getEmail());
            registration.setEventCode(updatedRequest.getEventCode());
            registration.setTicketCode(updatedRequest.getTicketCode());
            registration.setAmount(updatedRequest.getAmount());
            registrationRepository.save(registration);
        } else {
            throw new IllegalArgumentException("Registration with id " + id + " not found.");
        }
    }

    private RegistrationResponse mapToRegistrationResponse(Registration registration) {
        return RegistrationResponse.builder()
                .id(String.valueOf(registration.getId()))
                .registrationCode(registration.getRegistrationCode())
                .firstName(registration.getFirstName())
                .lastName(registration.getLastName())
                .email(registration.getEmail())
                .eventCode(registration.getEventCode())
                .ticketCode(registration.getTicketCode())
                .amount(registration.getAmount())
                .build();
    }

    public void deleteRegistrationById(Integer id) {
        Optional<Registration> registrationOpt = registrationRepository.findById(String.valueOf(id));
        if (registrationOpt.isPresent()) {
            registrationRepository.delete(registrationOpt.get());
        } else {
            throw new IllegalArgumentException("Registration with code " + id + " not found.");
        }
    }
}
