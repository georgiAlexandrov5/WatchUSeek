package softuni.WatchUSeek.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.entities.Review;
import softuni.WatchUSeek.data.models.service.ReviewServiceModel;
import softuni.WatchUSeek.errors.EventNotFoundException;
import softuni.WatchUSeek.errors.ReviewNotFoundException;
import softuni.WatchUSeek.repositories.ReviewRepository;
import softuni.WatchUSeek.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.EVENT_NOT_FOUND;
import static softuni.WatchUSeek.errors.Constants.REVIEW_NOT_FOUND;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ModelMapper mapper;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ModelMapper mapper, ReviewRepository reviewRepository) {
        this.mapper = mapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewServiceModel createReview(ReviewServiceModel reviewServiceModel) {
        Review review = this.mapper.map(reviewServiceModel, Review.class);

        return this.mapper.map(this.reviewRepository.saveAndFlush(review), ReviewServiceModel.class);
    }

    @Override
    public ReviewServiceModel findReviewById(String id) {
        return this.reviewRepository.findById(id)
                .map(r -> this.mapper.map(r, ReviewServiceModel.class))
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND));
    }

    @Override
    public void deleteReview(String id) {
        Review event = this.reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(EVENT_NOT_FOUND));

        this.reviewRepository.delete(event);
    }

    @Override
    public List<ReviewServiceModel> findAllReviews() {
        return this.reviewRepository.findAllAndOrderByRating()
                .stream()
                .map(c -> this.mapper.map(c, ReviewServiceModel.class))
                .collect(Collectors.toList());
    }

}

