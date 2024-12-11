package fact.it.eventservice;

import fact.it.eventservice.dto.EventRequest;
import fact.it.eventservice.dto.EventResponse;
import fact.it.eventservice.model.Event;
import fact.it.eventservice.repository.EventRepository;
import fact.it.eventservice.service.EventService;
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
public class EventServiceUnitTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void testCreateEvent() {
        // Arrange
        EventRequest eventRequest = new EventRequest();
        eventRequest.setEventCode("EVT123");
        eventRequest.setType("Conference");
        eventRequest.setName("Tech Summit 2024");
        eventRequest.setDescription("Annual Tech Conference");

        Event event = Event.builder()
                .eventCode(eventRequest.getEventCode())
                .type(eventRequest.getType())
                .name(eventRequest.getName())
                .description(eventRequest.getDescription())
                .build();

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        eventService.createEvent(eventRequest);

        // Assert
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void testGetAllEvents() {
        // Arrange
        Event event = new Event();
        event.setId("1");
        event.setEventCode("EVT123");
        event.setType("Conference");
        event.setName("Tech Summit 2024");
        event.setDescription("Annual Tech Conference");

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));

        // Act
        List<EventResponse> events = eventService.getAllEvents();

        // Assert
        assertEquals(1, events.size());
        assertEquals("EVT123", events.get(0).getEventCode());
        assertEquals("Conference", events.get(0).getType());
        assertEquals("Tech Summit 2024", events.get(0).getName());
        assertEquals("Annual Tech Conference", events.get(0).getDescription());

        verify(eventRepository, times(1)).findAll();
    }

    @Test
    public void testGetEventByEventCode() {
        // Arrange
        Event event = new Event();
        event.setId("1");
        event.setEventCode("EVT123");
        event.setType("Conference");
        event.setName("Tech Summit 2024");
        event.setDescription("Annual Tech Conference");

        when(eventRepository.findByEventCode("EVT123")).thenReturn(Optional.of(event));

        // Act
        EventResponse eventResponse = eventService.getEventByEventCode("EVT123");

        // Assert
        assertEquals("1", eventResponse.getId());
        assertEquals("EVT123", eventResponse.getEventCode());
        assertEquals("Conference", eventResponse.getType());
        assertEquals("Tech Summit 2024", eventResponse.getName());
        assertEquals("Annual Tech Conference", eventResponse.getDescription());

        verify(eventRepository, times(1)).findByEventCode("EVT123");
    }

    @Test
    public void testGetEventByEventCodeNotFound() {
        // Arrange
        when(eventRepository.findByEventCode("EVT999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> eventService.getEventByEventCode("EVT999"));

        verify(eventRepository, times(1)).findByEventCode("EVT999");
    }

    @Test
    public void testUpdateEvent() {
        // Arrange
        EventRequest eventRequest = new EventRequest();
        eventRequest.setEventCode("EVT124");
        eventRequest.setType("Workshop");
        eventRequest.setName("Coding Bootcamp");
        eventRequest.setDescription("Hands-on coding workshop");

        Event existingEvent = new Event();
        existingEvent.setId("1");
        existingEvent.setEventCode("EVT123");
        existingEvent.setType("Conference");
        existingEvent.setName("Tech Summit 2024");
        existingEvent.setDescription("Annual Tech Conference");

        when(eventRepository.findById("1")).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(existingEvent);

        // Act
        eventService.updateEvent("1", eventRequest);

        // Assert
        verify(eventRepository, times(1)).findById("1");
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    public void testDeleteEventById() {
        // Arrange
        Event event = new Event();
        event.setId("1");
        event.setEventCode("EVT123");
        event.setType("Conference");
        event.setName("Tech Summit 2024");
        event.setDescription("Annual Tech Conference");

        when(eventRepository.findById("1")).thenReturn(Optional.of(event));

        // Act
        eventService.deleteEventById("1");

        // Assert
        verify(eventRepository, times(1)).findById("1");
        verify(eventRepository, times(1)).delete(event);
    }
}
