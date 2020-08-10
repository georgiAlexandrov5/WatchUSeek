package softuni.WatchUSeek.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.WatchUSeek.data.models.binding.ReviewCreateBindingModel;
import softuni.WatchUSeek.data.models.service.ReviewServiceModel;
import softuni.WatchUSeek.data.models.view.ReviewViewModel;
import softuni.WatchUSeek.service.ReviewService;
import softuni.WatchUSeek.utils.CloudinaryService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController extends BaseController {
    private final ModelMapper mapper;
    private final ReviewService reviewService;
    private final CloudinaryService cloudinaryService;


    @Autowired
    public ReviewController(ModelMapper mapper, ReviewService reviewService, CloudinaryService cloudinaryService) {
        this.mapper = mapper;
        this.reviewService = reviewService;
        this.cloudinaryService = cloudinaryService;
    }




    @GetMapping("/add")
    @PageTitle("Add Review")
    public ModelAndView addReview(@ModelAttribute(name = "model") ReviewCreateBindingModel model) {
        return view("review/add-review");
    }


    @PostMapping("/add")
    public ModelAndView addReviewConfirm(@ModelAttribute(name = "model") ReviewCreateBindingModel model,
                                         BindingResult bindingResult) throws IOException {

        ReviewServiceModel reviewServiceModel = this.mapper.map(model, ReviewServiceModel.class);
  //      reviewServiceModel.setPictureUrl(this.cloudinaryService.uploadImage(model.getPictureUrl()));


        this.reviewService.createReview(reviewServiceModel);

        return super.redirect("/reviews/all");
    }

    @GetMapping("/all")
    @PageTitle("All Reviews")
    public ModelAndView allReviews(ModelAndView modelAndView) {
        List<ReviewServiceModel> reviews = this.reviewService.findAllReviews();
        modelAndView.addObject("reviews", reviews);
        return super.view("review/all-reviews", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Review Details")
    public ModelAndView reviewDetails(@PathVariable String id, ModelAndView modelAndView) {
        ReviewViewModel review = this.mapper.map(this.reviewService.findReviewById(id), ReviewViewModel.class);
        modelAndView.addObject("review",review);
        return super.view("review/details-review", modelAndView);
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteEventConfirm(@PathVariable String id) {
        this.reviewService.deleteReview(id);

        return super.redirect("/reviews/all");
    }

}
