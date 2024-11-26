package fact.it.eventservice.repository;

import fact.it.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, Integer> {

    Optional<Event> findByEventCode(String eventCode); // New method added
}
