package softuni.WatchUSeek.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.Strap;

import java.util.Optional;

@Repository
public interface StrapRepository extends JpaRepository<Strap, String> {
    Optional<Strap> findByName(String name);

}
