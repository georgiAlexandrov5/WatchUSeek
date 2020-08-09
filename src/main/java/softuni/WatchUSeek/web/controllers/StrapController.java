package softuni.WatchUSeek.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.WatchUSeek.data.models.binding.StrapCreateBindingModel;
import softuni.WatchUSeek.data.models.service.StrapServiceModel;
import softuni.WatchUSeek.data.models.view.StrapViewModel;
import softuni.WatchUSeek.errors.StrapNotFoundException;
import softuni.WatchUSeek.service.StrapService;
import softuni.WatchUSeek.utils.CloudinaryService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/straps")
public class StrapController extends BaseController {

    private final StrapService strapService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public StrapController(StrapService strapService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.strapService = strapService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PageTitle("Add Strap")
    public ModelAndView addStrap(@ModelAttribute(name = "model") StrapCreateBindingModel model) {
        return view("strap/add-strap");
    }

    @PostMapping("/add")
    public ModelAndView addStrapConfirm(@ModelAttribute(name = "model") StrapCreateBindingModel model) {
        StrapServiceModel strapServiceModel = this.modelMapper.map(model, StrapServiceModel.class);

        this.strapService.addStrap(strapServiceModel);

        return super.redirect("straps/all");
    }

    @GetMapping("/all")
    @PageTitle("All Straps")
    public ModelAndView allStraps(ModelAndView modelAndView){
        List<StrapServiceModel> straps = this.strapService.findAll();
        modelAndView.addObject("straps", straps);
        return super.view("strap/all-straps", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Strap")
    public ModelAndView editStrap(@PathVariable String id, ModelAndView modelAndView){
        StrapServiceModel strapServiceModel = this.strapService.findStrapById(id);

        modelAndView.addObject("strap", strapServiceModel);
        modelAndView.addObject("strapId", id);


        return super.view("strap/edit-strap",modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editStrapConfirm(@PathVariable String id,@ModelAttribute StrapCreateBindingModel model){
        this.strapService.editById(id, this.modelMapper.map(model, StrapServiceModel.class));

        return super.redirect("/straps/all");
    }





    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Strap Details")
    public ModelAndView strapDetails(@PathVariable String id, ModelAndView modelAndView) {
        StrapViewModel strap = this.modelMapper.map(this.strapService.findStrapById(id), StrapViewModel.class);
        modelAndView.addObject("strap",strap);
        return super.view("strap/details-strap", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteStrapConfirm(@PathVariable String id) {
        this.strapService.deleteStrap(id);

        return super.redirect("/home");
    }

    @ExceptionHandler({StrapNotFoundException.class})
    public ModelAndView handleStrapNotFound(StrapNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatus());

        return modelAndView;
    }
}
