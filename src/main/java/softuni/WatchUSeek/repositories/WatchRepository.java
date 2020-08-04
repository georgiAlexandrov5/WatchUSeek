package softuni.WatchUSeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.Watch;


@Repository
public interface WatchRepository extends JpaRepository<Watch, String> {



}
