package fact.it.registrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String registrationCode;
    private String firstName;
    private String lastName;
    private String email;
    private String eventCode;
    private String ticketCode;
    private Double amount;
}
