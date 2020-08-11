package softuni.WatchUSeek.services.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.WatchUSeek.data.entities.Review;
import softuni.WatchUSeek.data.entities.Watch;
import softuni.WatchUSeek.data.models.service.ReviewServiceModel;
import softuni.WatchUSeek.errors.ReviewNotFoundException;
import softuni.WatchUSeek.repositories.ReviewRepository;
import softuni.WatchUSeek.service.ReviewService;
import softuni.WatchUSeek.services.base.ServiceTestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewServiceTest extends ServiceTestBase {

    List<Review> reviews;
    @MockBean
    ReviewRepository reviewRepository;


    ReviewServiceModel reviewServiceModel;
    private final ModelMapper modelMapper;


    @Autowired
    ReviewService reviewService;

    @Autowired
    public ReviewServiceTest(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @BeforeEach
    protected void beforeEach() {
        Review review = new Review();

        reviews = new ArrayList<>();
        Mockito.when(reviewRepository.findAll()).thenReturn(reviews);

        reviewServiceModel = new ReviewServiceModel();
        reviewServiceModel.setTitle("Review");
        reviewServiceModel.setRating(9);
        reviewServiceModel.setDescription("Description");
        reviewServiceModel.setPictureUrl("PictureUrl.jpg");


    }

    @Test
    void findAllReviews_ShouldReturnCorrect() {
        Review review1 = new Review();
        Review review2 = new Review();

        review1.setId("1");
        review2.setId("2");
        reviews.add(review1);
        reviews.add(review2);

        List<ReviewServiceModel> reviewServiceModelList = reviewService.findAllReviews();


        Assert.assertEquals(reviews.size(), reviewServiceModelList.size());
        Assert.assertEquals(reviews.get(0).getId(), reviewServiceModelList.get(0).getId());
        Assert.assertEquals(reviews.get(1).getId(), reviewServiceModelList.get(1).getId());
    }

    @Test
    void findReviewById_whenInvalidId_shouldThrow() {
        String id = "2";
        Mockito.when(this.reviewRepository.findById("1"))
                .thenReturn(Optional.empty());
        assertThrows(ReviewNotFoundException.class,() -> this.reviewService.findReviewById(id));

    }

    @Test
    void findAllReviews_whenEmpty_shouldReturnEmpty() {
        reviews.clear();

        List<ReviewServiceModel> events = reviewService.findAllReviews();

        Assert.assertEquals(0, events.size());
    }

    @Test
    void delete_whenExist_shouldDelete(){

        String id="1";
        Review review=new Review();
        review.setId(id);
        Mockito.when(reviewRepository.findById(id)).thenReturn(Optional.of(review)).thenThrow(RuntimeException.class);
        this.reviewService.deleteReview(id);
        assertThrows(RuntimeException.class,
                ()->this.reviewService.deleteReview(id));
    }


}
