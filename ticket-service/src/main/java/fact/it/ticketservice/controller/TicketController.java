package fact.it.ticketservice.controller;

import fact.it.ticketservice.dto.TicketRequest;
import fact.it.ticketservice.dto.TicketResponse;
import fact.it.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTicket(@RequestBody TicketRequest ticketRequest) {
        ticketService.createProduct(ticketRequest);
    }

    @GetMapping("/{ticketCode}")
    @ResponseStatus(HttpStatus.OK)
    public TicketResponse getAllTicketsByTicketCode(@PathVariable String ticketCode) {
        return ticketService.getTicketByTicketCode(ticketCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // Update a ticket by its ticketCode
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateTicket(@PathVariable Integer id, @RequestBody TicketRequest ticketRequest) {
        try {
            ticketService.updateTicket(id, ticketRequest);
            return ResponseEntity.ok("Ticket updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteTicket(@PathVariable Integer id) {
        try {
            ticketService.deleteTicketById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
