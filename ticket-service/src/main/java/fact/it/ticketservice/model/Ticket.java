package fact.it.ticketservice.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String ticketCode;
    private String type;
    private Integer amountLeft;
    private Double price;
}
