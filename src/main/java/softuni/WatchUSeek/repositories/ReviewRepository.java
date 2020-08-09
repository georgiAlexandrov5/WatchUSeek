package softuni.WatchUSeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

   @Query("SELECT r from Review r order by r.rating")
    List<Review> findAllAndOrderByRating();
}
