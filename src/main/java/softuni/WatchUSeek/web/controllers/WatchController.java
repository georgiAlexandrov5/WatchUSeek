package softuni.WatchUSeek.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.WatchUSeek.data.entities.User;
import softuni.WatchUSeek.data.models.binding.WatchCreateBindingModel;
import softuni.WatchUSeek.data.models.service.WatchServiceModel;
import softuni.WatchUSeek.errors.WatchNotFoundException;
import softuni.WatchUSeek.service.UserService;
import softuni.WatchUSeek.service.WatchService;
import softuni.WatchUSeek.utils.CloudinaryService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/watches")
public class WatchController extends BaseController {

    private final ModelMapper modelMapper;
    private final WatchService watchService;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;


    @Autowired
    public WatchController(ModelMapper modelMapper, WatchService watchService, UserService userService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.watchService = watchService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PageTitle("Add Watch")
    public ModelAndView addWatch(@ModelAttribute(name = "model") WatchCreateBindingModel model) {
        return view("watch/add-watch");
    }

    @PostMapping("/add")
    public ModelAndView addWatchConfirm(@Validated @ModelAttribute(name = "model") WatchCreateBindingModel model,
                                         Principal principal) {

        WatchServiceModel watchServiceModel = this.modelMapper.map(model, WatchServiceModel.class);

        this.watchService.addWatch(watchServiceModel);

        return super.redirect("/watches/all");
    }

    @GetMapping("/all")
    @PageTitle("All Watches")
    public ModelAndView allWatches(ModelAndView modelAndView) {
        List<WatchServiceModel> watches = this.watchService.findAll();
        modelAndView.addObject("watches", watches);
        return super.view("watch/all-watches", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Watch")
    public ModelAndView editWatch(@PathVariable String id, ModelAndView modelAndView) {
        WatchServiceModel watchServiceModel = this.watchService.findWatchById(id);

        modelAndView.addObject("watch", watchServiceModel);
        modelAndView.addObject("watchId", id);


        return super.view("watch/edit-watch", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editWatchConfirm(@PathVariable String id, @ModelAttribute WatchCreateBindingModel model) {
        this.watchService.editById(id, this.modelMapper.map(model, WatchServiceModel.class));

        return super.redirect("/watches/all");
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Watch Details")
    public ModelAndView watchDetails(@PathVariable String id, ModelAndView modelAndView) {
        WatchServiceModel watch = this.watchService.findWatchById(id);
        modelAndView.addObject("watch", watch);
        return super.view("watch/details-watch", modelAndView);
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteWatchConfirm(@PathVariable String id) {
        this.watchService.deleteWatch(id);

        return super.redirect("/watches/all");
    }

    @ExceptionHandler({WatchNotFoundException.class})
    public ModelAndView handleWatchNotFound(WatchNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatus());

        return modelAndView;
    }
}