package softuni.WatchUSeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.Watch;

import java.util.Optional;


@Repository
public interface WatchRepository extends JpaRepository<Watch, String> {

    Optional<Watch> findByRefNumber(String refNumber);

}
