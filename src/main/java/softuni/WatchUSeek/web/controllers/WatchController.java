package softuni.WatchUSeek.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/watches")
public class WatchController extends BaseController {

    private final ModelMapper modelMapper;


    public WatchController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
