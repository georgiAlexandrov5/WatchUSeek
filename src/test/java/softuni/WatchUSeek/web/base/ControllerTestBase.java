package softuni.WatchUSeek.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import softuni.WatchUSeek.base.TestBase;

@AutoConfigureMockMvc
public class ControllerTestBase extends TestBase {
    @Autowired
    protected MockMvc mockMvc;
}

