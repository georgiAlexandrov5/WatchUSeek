package softuni.WatchUSeek.service.interfaces;

import softuni.WatchUSeek.data.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRoles();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
