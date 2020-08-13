package softuni.WatchUSeek.web.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import softuni.WatchUSeek.repositories.UserRepository;
import softuni.WatchUSeek.web.base.ControllerTestBase;


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
