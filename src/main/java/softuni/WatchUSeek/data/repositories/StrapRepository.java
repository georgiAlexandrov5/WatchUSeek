package softuni.WatchUSeek.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.Strap;

@Repository
public interface StrapRepository extends JpaRepository<Strap, String> {
}
