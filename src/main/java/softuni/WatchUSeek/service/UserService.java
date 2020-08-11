package softuni.WatchUSeek.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.WatchUSeek.data.models.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> findAll();

    void makeAdmin(String id);

    UserServiceModel findById(String id);

    UserServiceModel checkIfUsernameExists(String username);




}
