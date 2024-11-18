package fact.it.ticketservice.repository;

import fact.it.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByTicketCodeIn(List<String> skuCode);
    Optional<Ticket> findByTicketCode(String ticketCode);
}
