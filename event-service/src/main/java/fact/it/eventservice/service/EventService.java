package fact.it.eventservice.service;

import fact.it.eventservice.model.Event;
import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;

    public void createEvent(EventRequest eventRequest) {
        Event event = Event.builder()
                .eventCode(eventRequest.getEventCode())
                .type(eventRequest.getType())
                .name(eventRequest.getName())
                .description(eventRequest.getDescription())
                .build();

        eventRepository.save(event);
    }

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map(this::mapToEventResponse).toList();
    }

    public EventResponse getEventByEventCode(String eventCode) {
        Event event = eventRepository.findByEventCode(eventCode).orElseThrow(() -> new IllegalArgumentException("Event not found"));

        return mapToEventResponse(event);
    }

    public void updateEvent(String id, EventRequest eventRequest) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        event.setType(eventRequest.getType());
        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());

        eventRepository.save(event);
    }

    private EventResponse mapToEventResponse(Event event) {
        return EventResponse.builder()
                .id(String.valueOf(event.getId()))
                .eventCode(event.getEventCode())
                .type(event.getType())
                .name(event.getName())
                .description(event.getDescription())
                .build();
    }

    public void deleteEventById(Integer id) {
        Event event = eventRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        eventRepository.delete(event);
    }
}
