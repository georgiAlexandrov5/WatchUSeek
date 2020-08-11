package softuni.WatchUSeek.services.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import softuni.WatchUSeek.data.entities.User;
import softuni.WatchUSeek.data.models.service.UserServiceModel;
import softuni.WatchUSeek.errors.UserNotFoundException;
import softuni.WatchUSeek.repositories.UserRepository;
import softuni.WatchUSeek.service.RoleService;
import softuni.WatchUSeek.service.UserService;
import softuni.WatchUSeek.services.base.ServiceTestBase;
import softuni.WatchUSeek.validations.user.UserRegisterValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends ServiceTestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleService roleService;

    @MockBean
    BCryptPasswordEncoder encoder;

    private final ModelMapper modelMapper;
    private User user;
    private String expected;
    private String actual;
    private UserRegisterValidator validator;


    @Autowired
    private UserService userService;

    @Autowired
    public UserServiceTest(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected void beforeEach() {
        user = new User();
        user.setUsername("Pesho");
        user.setEmail("pesho333@abv.bg");
        user.setPassword("@Password123");
        user.setId("1");
    }


    @Test
    void findUserById_whenValidId_shouldReturnUser() {


        Mockito.when(this.userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        UserServiceModel userModel = this.userService.findById(user.getId());

        actual = user.getEmail();
        expected = userModel.getEmail();

        Assert.assertEquals(actual, expected);
    }

    @Test
    void findUserById_whenInvalidId_shouldThrowException() {
        String id = "2";
        Mockito.when(this.userRepository.findById("2"))
                .thenReturn(Optional.empty());
        assertThrows(
                UserNotFoundException.class,
                () -> this.userService.findById(id));

    }

    @Test
    void findUserByUsername_whenValidUsername_shouldReturnUser() {
        Mockito.when(this.userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        expected = user.getUsername();
        actual = this.userService.findUserByUsername(this.user.getUsername()).getUsername();

        Assert.assertEquals(actual, expected);

    }

    @Test
    void findUserByUsername_whenInvalidUsername_shouldThrowException() {
        String username = "Gosho";
        Mockito.when(this.userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(
                UsernameNotFoundException.class,
                () -> this.userService.findUserByUsername(username));
    }

    @Test
    void findAllUsers_withCorrectData_shouldReturnCorrectSize() {
        User user2 = new User();
        user.setUsername("Gosho");
        user.setEmail("gogo123@abv.bg");
        user.setPassword("@Gosho123");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        Mockito.when(this.userRepository.findAll()).thenReturn(users);

        Assert.assertEquals(2, userService.findAll().size());

    }

    @Test
    void findAllUsers_withNoUsers_shouldReturnEmpty() {
        List<User> users = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Assert.assertEquals(0, userService.findAll().size());
    }

    @Test
    void checkIfUsernameExists_ifExists_shouldReturnCorrectModel() {
        String username = "petkan";
        User user = new User();
        user.setUsername(username);

        Mockito.when(this.userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserServiceModel result = this.userService.checkIfUsernameExists(username);

        actual = user.getUsername();
        expected = result.getUsername();

        assertEquals(actual, expected);
    }

    @Test
    void checkIfUsernameExists_ifNot_shouldReturnNull() {
        String username = "petkan";
        Mockito.when(this.userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertNull(this.userService.checkIfUsernameExists(username));
    }


    @Test
    void makeAdmin_whenNoUser_ShouldThrow(){
        String id="invalid_Id";
        Mockito.when(this.userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                ()->this.userService.makeAdmin(id));
    }


    @Test
    void registerUser_whenCorrectData_ShouldReturnCorrect(){
         UserServiceModel model = new UserServiceModel();
         model.setUsername("Petkan123");
         model.setPassword("@Petkan123");
         model.setEmail("petkan@abv.bg");

         this.userService.registerUser(model);

         assertNotNull(this.userRepository.findByUsername("Petkan123"));
    }


    @Test
    public void editUserProfile_WhenAllOk_ShouldWork() {
        expected = "@OtherPassword11";
        user.setPassword(expected );
        actual = user.getPassword();


        Mockito.when(userRepository.findByUsername("Pesho"))
                .thenReturn(Optional.of(user));
        Mockito.when(encoder.matches("@OtherPassword11", "@OtherPassword11"))
                .thenReturn(true);
        Mockito.when(encoder.encode("@OtherPassword11"))
                .thenReturn("@OtherPassword11");
        assertEquals(actual , expected);
    }

    @Test
    void deleteUser_shouldTReturnCorrect() {
        String id = "1";
        this.userRepository.deleteById(id);

        Mockito.when(this.userRepository.findById(id)).thenReturn(null);
        assertNull(
                this.userRepository.findById(id));

    }



}
