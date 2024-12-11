package fact.it.registrationservice;

import fact.it.registrationservice.dto.*;
import fact.it.registrationservice.model.Registration;
import fact.it.registrationservice.repository.RegistrationRepository;
import fact.it.registrationservice.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceUnitTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(registrationService, "ticketServiceBaseUrl", "http://localhost:8081");
        ReflectionTestUtils.setField(registrationService, "eventServiceBaseUrl", "http://localhost:8082");
    }

    @Test
    public void testCreateRegistration_Success() {
        // Arrange
        String ticketCode = "TKT123";
        String eventCode = "EVT456";

        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .registrationCode("REG001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .ticketCode(ticketCode)
                .eventCode(eventCode)
                .amount(2.0)
                .build();

        TicketResponse ticketResponse = TicketResponse.builder()
                .eventCode(eventCode)
                .amountLeft(10)
                .price(50.0)
                .build();

        EventResponse eventResponse = EventResponse.builder()
                .eventCode(eventCode)
                .isInStock(true)
                .build();

        Registration registration = Registration.builder()
                .registrationCode("REG001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .ticketCode(ticketCode)
                .eventCode(eventCode)
                .amount(2.0)
                .build();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(),  any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(TicketResponse[].class)).thenReturn(Mono.just(new TicketResponse[]{ticketResponse}));
        when(responseSpec.bodyToMono(EventResponse[].class)).thenReturn(Mono.just(new EventResponse[]{eventResponse}));


        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        registrationService.createRegistration(registrationRequest);

        // Assert
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    public void testCreateRegistration_Failure_TicketUnavailable() {
        // Arrange
        String ticketCode = "TKT123";
        String eventCode = "EVT456";

        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .registrationCode("REG002")
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .ticketCode(ticketCode)
                .eventCode(eventCode)
                .amount(1.0)
                .build();

        TicketResponse ticketResponse = TicketResponse.builder()
                .eventCode(eventCode)
                .amountLeft(0)
                .price(50.0)
                .build();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("http://localhost:8081/api/ticket/{ticketCode}"), eq(ticketCode))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(TicketResponse.class)).thenReturn(Mono.just(ticketResponse));

        // Act & Assert
        registrationService.createRegistration(registrationRequest);
        verify(registrationRepository, times(0)).save(any(Registration.class));
    }

    @Test
    public void testGetAllRegistrations() {
        // Arrange
        Registration registration1 = Registration.builder()
                .id(1)
                .registrationCode("REG001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .ticketCode("TKT123")
                .eventCode("EVT456")
                .amount(2.0)
                .build();

        Registration registration2 = Registration.builder()
                .id(2)
                .registrationCode("REG002")
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .ticketCode("TKT124")
                .eventCode("EVT457")
                .amount(1.0)
                .build();

        when(registrationRepository.findAll()).thenReturn(Arrays.asList(registration1, registration2));

        // Act
        List<RegistrationResponse> registrations = registrationService.getAllRegistrations();

        // Assert
        assertEquals(2, registrations.size());
        assertEquals("REG001", registrations.get(0).getRegistrationCode());
        assertEquals("REG002", registrations.get(1).getRegistrationCode());
        verify(registrationRepository, times(1)).findAll();
    }

    @Test
    public void testGetRegistrationByRegistrationCode() {
        // Arrange
        Registration registration = Registration.builder()
                .id(1)
                .registrationCode("REG001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .ticketCode("TKT123")
                .eventCode("EVT456")
                .amount(2.0)
                .build();

        when(registrationRepository.findByRegistrationCode("REG001")).thenReturn(Optional.of(registration));

        // Act
        Optional<RegistrationResponse> registrationResponse = registrationService.getRegistrationByRegistrationCode("REG001");

        // Assert
        assertTrue(registrationResponse.isPresent());
        assertEquals("REG001", registrationResponse.get().getRegistrationCode());
        verify(registrationRepository, times(1)).findByRegistrationCode("REG001");
    }

    @Test
    public void testDeleteRegistrationById() {
        // Arrange
        Registration registration = Registration.builder()
                .id(1)
                .registrationCode("REG001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .ticketCode("TKT123")
                .eventCode("EVT456")
                .amount(2.0)
                .build();

        when(registrationRepository.findById(1)).thenReturn(Optional.of(registration));

        // Act
        registrationService.deleteRegistrationById(1);

        // Assert
        verify(registrationRepository, times(1)).findById(1);
        verify(registrationRepository, times(1)).delete(registration);
    }
}
