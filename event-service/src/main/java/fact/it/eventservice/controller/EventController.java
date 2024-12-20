package fact.it.eventservice.controller;

import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody EventRequest eventRequest) {
        eventService.createEvent(eventRequest);
    }

    @GetMapping("/{eventCode}")
    @ResponseStatus(HttpStatus.OK)
    public EventResponse getAllEventsByEventCode(@PathVariable String eventCode) {
        return eventService.getEventByEventCode(eventCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Update an event by its eventCode
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateEvent(@PathVariable String id, @RequestBody EventRequest eventRequest) {
        try {
            eventService.updateEvent(id, eventRequest);
            return ResponseEntity.ok("Event updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        try {
            eventService.deleteEventById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
