package softuni.WatchUSeek.web.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.WatchUSeek.service.interfaces.WatchService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class WatchApiController {

    private final WatchService watchService;
    private final ModelMapper modelMapper;

    @Autowired
    public WatchApiController(WatchService watchService, ModelMapper modelMapper) {
        this.watchService = watchService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(value = "/watches/all")
    public List<WatchResponseModel> getWatches(HttpSession session){
        return watchService.findAll().stream()
                .map(w ->{
                    WatchResponseModel model = modelMapper.map(w, WatchResponseModel.class);
                    return model;
                }).collect(Collectors.toList());
    }
}
