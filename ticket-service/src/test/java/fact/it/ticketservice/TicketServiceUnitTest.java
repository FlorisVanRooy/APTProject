package fact.it.ticketservice;

import fact.it.ticketservice.dto.TicketRequest;
import fact.it.ticketservice.dto.TicketResponse;
import fact.it.ticketservice.model.Ticket;
import fact.it.ticketservice.repository.TicketRepository;
import fact.it.ticketservice.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceUnitTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void testCreateTicket() {
        // Arrange
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setTicketCode("TKT123");
        ticketRequest.setType("VIP");
        ticketRequest.setAmountLeft(50);
        ticketRequest.setPrice(150.0);

        Ticket ticket = Ticket.builder()
                .ticketCode(ticketRequest.getTicketCode())
                .type(ticketRequest.getType())
                .amountLeft(ticketRequest.getAmountLeft())
                .price(ticketRequest.getPrice())
                .build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // Act
        ticketService.createProduct(ticketRequest);

        // Assert
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testGetAllTickets() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTicketCode("TKT123");
        ticket.setType("VIP");
        ticket.setAmountLeft(50);
        ticket.setPrice(150.0);

        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket));

        // Act
        List<TicketResponse> tickets = ticketService.getAllTickets();

        // Assert
        assertEquals(1, tickets.size());
        assertEquals("TKT123", tickets.get(0).getTicketCode());
        assertEquals("VIP", tickets.get(0).getType());
        assertEquals(50, tickets.get(0).getAmountLeft());
        assertEquals(150.0, tickets.get(0).getPrice());

        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void testGetTicketByTicketCode() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTicketCode("TKT123");
        ticket.setType("VIP");
        ticket.setAmountLeft(50);
        ticket.setPrice(150.0);

        when(ticketRepository.findByTicketCode("TKT123")).thenReturn(Optional.of(ticket));

        // Act
        TicketResponse ticketResponse = ticketService.getTicketByTicketCode("TKT123");

        // Assert
        assertEquals("1", ticketResponse.getId());
        assertEquals("TKT123", ticketResponse.getTicketCode());
        assertEquals("VIP", ticketResponse.getType());
        assertEquals(50, ticketResponse.getAmountLeft());
        assertEquals(150.0, ticketResponse.getPrice());

        verify(ticketRepository, times(1)).findByTicketCode("TKT123");
    }

    @Test
    public void testGetTicketByTicketCodeNotFound() {
        // Arrange
        when(ticketRepository.findByTicketCode("TKT999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketByTicketCode("TKT999"));

        verify(ticketRepository, times(1)).findByTicketCode("TKT999");
    }

    @Test
    public void testUpdateTicket() {
        // Arrange
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setTicketCode("TKT124");
        ticketRequest.setType("Regular");
        ticketRequest.setAmountLeft(100);
        ticketRequest.setPrice(50.0);

        Ticket existingTicket = new Ticket();
        existingTicket.setId(1);
        existingTicket.setTicketCode("TKT123");
        existingTicket.setType("VIP");
        existingTicket.setAmountLeft(50);
        existingTicket.setPrice(150.0);

        when(ticketRepository.findById(1)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        // Act
        ticketService.updateTicket(1, ticketRequest);

        // Assert
        verify(ticketRepository, times(1)).findById(1);
        verify(ticketRepository, times(1)).save(existingTicket);
    }

    @Test
    public void testDeleteTicketById() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTicketCode("TKT123");
        ticket.setType("VIP");
        ticket.setAmountLeft(50);
        ticket.setPrice(150.0);

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        // Act
        ticketService.deleteTicketById(1);

        // Assert
        verify(ticketRepository, times(1)).findById(1);
        verify(ticketRepository, times(1)).delete(ticket);
    }
}
