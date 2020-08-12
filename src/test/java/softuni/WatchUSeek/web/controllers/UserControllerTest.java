package softuni.WatchUSeek.web.controllers;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import softuni.WatchUSeek.data.entities.Role;
import softuni.WatchUSeek.data.entities.User;
import softuni.WatchUSeek.data.models.binding.UserRegisterBindingModel;
import softuni.WatchUSeek.data.models.service.RoleServiceModel;
import softuni.WatchUSeek.data.models.service.UserServiceModel;
import softuni.WatchUSeek.repositories.UserRepository;
import softuni.WatchUSeek.validations.UserValidationService;
import softuni.WatchUSeek.validations.user.UserRegisterValidator;
import softuni.WatchUSeek.web.base.ControllerTestBase;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UserControllerTest extends ControllerTestBase {

    @MockBean
    UserRepository userRepository;
    @MockBean
    BindingResult bindingResult;
    @Mock
    ModelMapper modelMapper;





    @Test
    void register_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"));


    }

    @Test
    void login_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
    }





}
