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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAllTicketsByTicketCode(@RequestParam List<String> ticketCode) {
        return ticketService.getAllProductsBySkuCode(ticketCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // Update a ticket by its ticketCode
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateTicket(@PathVariable String ticketCode, @RequestBody TicketRequest ticketRequest) {
        try {
            ticketService.updateTicket(ticketCode, ticketRequest);
            return ResponseEntity.ok("Ticket updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete a ticket by its ticketCode
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteTicket(@PathVariable String ticketCode) {
        try {
            ticketService.deleteTicketByTicketCode(ticketCode);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
