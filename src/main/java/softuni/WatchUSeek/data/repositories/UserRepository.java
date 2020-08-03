package softuni.WatchUSeek.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}