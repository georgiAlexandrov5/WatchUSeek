package softuni.WatchUSeek.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import softuni.WatchUSeek.web.base.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest extends ControllerTestBase {

    @Test
    public void index_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser("pesho")
    public void home_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/home"))
                .andExpect(view().name("home"));
    }
}
