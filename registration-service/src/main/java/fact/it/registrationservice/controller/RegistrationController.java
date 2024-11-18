package fact.it.registrationservice.controller;

import fact.it.registrationservice.dto.RegistrationRequest;
import fact.it.registrationservice.dto.RegistrationResponse;
import fact.it.registrationservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRegistration(@RequestBody RegistrationRequest registrationRequest) {
        registrationService.createRegistration(registrationRequest);
    }

    // Get a registration by its registrationCode using @RequestParam
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RegistrationResponse> getRegistrationByRegistrationCode(@RequestParam String registrationCode) {
        Optional<RegistrationResponse> registration = registrationService.getRegistrationByRegistrationCode(registrationCode);
        return registration.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RegistrationResponse> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    // Update a registration by its registrationCode using @RequestParam
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateRegistration(@RequestParam String registrationCode, @RequestBody RegistrationRequest registrationRequest) {
        try {
            registrationService.updateRegistration(registrationCode, registrationRequest);
            return ResponseEntity.ok("Registration updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete a registration by its registrationCode using @RequestParam
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteRegistration(@RequestParam String registrationCode) {
        try {
            registrationService.deleteRegistrationByRegistrationCode(registrationCode);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
