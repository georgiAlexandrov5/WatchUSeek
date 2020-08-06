package softuni.WatchUSeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
