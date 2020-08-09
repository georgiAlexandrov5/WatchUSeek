package softuni.WatchUSeek.service;


import softuni.WatchUSeek.data.models.service.ReviewServiceModel;

import java.util.List;

public interface ReviewService {

    ReviewServiceModel createReview(ReviewServiceModel reviewServiceModel);

    ReviewServiceModel findReviewById(String id);

    void deleteReview(String id);

    List<ReviewServiceModel> findAllReviews();
}
