package softuni.WatchUSeek.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.WatchUSeek.data.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findByAndAuthority(String authority);
}
