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
import softuni.WatchUSeek.errors.UserNameNotFreeException;
import softuni.WatchUSeek.errors.UserNotFoundException;
import softuni.WatchUSeek.errors.UserWrongCredentialsException;
import softuni.WatchUSeek.repositories.UserRepository;
import softuni.WatchUSeek.service.interfaces.RoleService;
import softuni.WatchUSeek.service.interfaces.UserService;
import softuni.WatchUSeek.service.validations.UserServiceModelValidator;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.*;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERNAME_INVALID_CREDENTIALS = "Account was not created, invalid credentials!";
    private static final String USERNAME_IS_NOT_FREE = "This username is already taken!";
    private static final String INCORRECT_PASSWORD = "Incorrect password";



    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserServiceModelValidator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           RoleService roleService,
                           BCryptPasswordEncoder passwordEncoder, UserServiceModelValidator validator) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_ID_NOT_FOUND));
        return this.mapper.map(user, UserServiceModel.class);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        if (!validator.isValid(userServiceModel)) {
            throw new UserWrongCredentialsException(USERNAME_INVALID_CREDENTIALS);
        }

        if (this.userRepository.findByUsername(userServiceModel.getUsername()).isPresent()) {
            throw new UserNameNotFreeException(USERNAME_IS_NOT_FREE);
        }
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
            throw new IllegalArgumentException(INCORRECT_PASSWORD);
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
