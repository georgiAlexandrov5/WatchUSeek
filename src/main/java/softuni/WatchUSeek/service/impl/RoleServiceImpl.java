package softuni.WatchUSeek.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.entities.Role;
import softuni.WatchUSeek.data.models.service.RoleServiceModel;
import softuni.WatchUSeek.repositories.RoleRepository;
import softuni.WatchUSeek.service.RoleService;

import java.util.Set;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.*;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }


    @Override
    public void seedRoles() {
        if (this.roleRepository.count() == 0){
            this.roleRepository.saveAndFlush(new Role(ROLE_ADMIN));
            this.roleRepository.saveAndFlush(new Role(ROLE_ROOT));
            this.roleRepository.saveAndFlush(new Role(ROLE_USER));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.mapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.mapper.map(this.roleRepository.findByAuthority(authority),RoleServiceModel.class);
    }
}
