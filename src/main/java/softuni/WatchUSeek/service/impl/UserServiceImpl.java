package softuni.WatchUSeek.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.entities.User;
import softuni.WatchUSeek.data.models.service.UserServiceModel;
import softuni.WatchUSeek.errors.Constants;
import softuni.WatchUSeek.errors.UserNotFoundException;
import softuni.WatchUSeek.repositories.UserRepository;
import softuni.WatchUSeek.service.RoleService;
import softuni.WatchUSeek.service.UserService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           RoleService roleService,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_ID_NOT_FOUND));
        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        this.roleService.seedRoles();

        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());

            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }

        User user = this.mapper.map(userServiceModel, User.class);
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

        userRepository.save(user);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.mapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(USER_ID_NOT_FOUND));

        if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("NOT CORRECT");
        }

        user.setPassword(userServiceModel.getPassword().isEmpty() ?
                user.getPassword() :
                passwordEncoder.encode(userServiceModel.getPassword()));
        user.setEmail(userServiceModel.getEmail());

        return this.mapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }



    @Override
    public List<UserServiceModel> findAll() {
        return userRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void makeAdmin(String id) {
        UserServiceModel userServiceModel = findUserByIdAndGetAuth(id);
        userServiceModel.getAuthorities().add(this.roleService.findByAuthority(ROLE_ADMIN));

        this.userRepository.saveAndFlush(this.mapper.map(userServiceModel, User.class));
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));
    }

    @Override
    public UserServiceModel checkIfUsernameExists(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return this.mapper.map(user, UserServiceModel.class);
    }

    public UserServiceModel findUserByIdAndGetAuth(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USER_ID_NOT_FOUND));

        UserServiceModel userServiceModel = this.mapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));

        return userServiceModel;
    }

}
