package softuni.WatchUSeek.services.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.WatchUSeek.data.entities.Role;
import softuni.WatchUSeek.data.models.service.RoleServiceModel;
import softuni.WatchUSeek.repositories.RoleRepository;
import softuni.WatchUSeek.service.RoleService;
import softuni.WatchUSeek.services.base.ServiceTestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.times;
import static softuni.WatchUSeek.errors.Constants.*;

public class RoleServiceTest extends ServiceTestBase {

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Test
    void seedRolesInBase_whenNoRoles_ShouldSeedCorrectRoles(){
        Mockito.when(roleRepository.count()).thenReturn(0L);
        this.roleService.seedRoles();

        ArgumentCaptor<Role> argument = ArgumentCaptor.forClass(Role.class);
        Mockito.verify(roleRepository,times(3)).saveAndFlush(argument.capture());

        List<Role> roles = argument.getAllValues();
        Assert.assertEquals(3,roles.size());
        Assert.assertEquals(ROLE_ADMIN,roles.get(0).getAuthority().toString());
        Assert.assertEquals(ROLE_ROOT,roles.get(1).getAuthority().toString());
        Assert.assertEquals(ROLE_USER,roles.get(2).getAuthority().toString());

    }

    @Test
    void findAllRole_whenExists_shouldReturnCorrect(){
        Role role1=new Role(ROLE_USER);
        Role role2=new Role(ROLE_ADMIN);
        Role role3=new Role(ROLE_ROOT);
        List<Role> roles=new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);

        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        Set<RoleServiceModel> result=roleService.findAllRoles();

        Assert.assertEquals(roles.size(),result.size());
    }

    @Test
    void findByAuthority_withValidInput_shouldReturnCorrect(){
        String authority= ROLE_ADMIN;
        Role role=new Role();
        role.setAuthority("ROLE_ADMIN");
        Mockito.when(roleRepository.findByAuthority(authority)).thenReturn(role);
        RoleServiceModel result=roleService.findByAuthority(authority);

        Assert.assertEquals(role.getAuthority(),result.getAuthority());
    }

}
