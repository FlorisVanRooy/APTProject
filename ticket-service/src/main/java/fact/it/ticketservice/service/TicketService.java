package fact.it.ticketservice.service;

import fact.it.ticketservice.model.Ticket;
import fact.it.ticketservice.dto.TicketRequest;
import fact.it.ticketservice.dto.TicketResponse;
import fact.it.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;

    public void createProduct(TicketRequest ticketRequest) {
        Ticket ticket = Ticket.builder()
                .type(ticketRequest.getType())
                .amountLeft(ticketRequest.getAmountLeft())
                .price(ticketRequest.getPrice())
                .build();

        ticketRepository.save(ticket);
    }

    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream().map(this::mapToTicketResponse).toList();
    }

    public List<TicketResponse> getAllProductsBySkuCode(List<String> ticketCode) {
        List<Ticket> tickets = ticketRepository.findByTicketCodeIn(ticketCode);

        return tickets.stream().map(this::mapToTicketResponse).toList();
    }

    public void updateTicket(String ticketCode, TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        ticket.setType(ticketRequest.getType());
        ticket.setAmountLeft(ticketRequest.getAmountLeft());
        ticket.setPrice(ticketRequest.getPrice());

        ticketRepository.save(ticket);
    }

    public void deleteTicketByTicketCode(String ticketCode) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        ticketRepository.delete(ticket);
    }

    private TicketResponse mapToTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .type(ticket.getType())
                .price(ticket.getPrice())
                .amountLeft(ticket.getAmountLeft())
                .build();
    }
}
