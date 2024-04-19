package alessia.LastProjectGestioneEventi.repositories;

import alessia.LastProjectGestioneEventi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventDAO extends JpaRepository<Event, UUID> {
Optional<Event>findBytitle(String title);
}
