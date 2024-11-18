package fact.it.registrationservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "registration")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String registrationCode;
    private String firstName;
    private String lastName;
    private String email;
    private String eventCode;
    private String ticketCode;
    private Double amount;
}
