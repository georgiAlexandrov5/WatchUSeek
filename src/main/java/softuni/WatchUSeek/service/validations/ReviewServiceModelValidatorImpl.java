package softuni.WatchUSeek.service.validations;

import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.models.service.ReviewServiceModel;

import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MAX_SIZE;
import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MIN_SIZE;

@Service
public class ReviewServiceModelValidatorImpl implements ReviewServiceModelValidator {
    @Override
    public boolean isValid(ReviewServiceModel model) {
        return isTitleValid(model.getTitle()) && isRatingValid(model.getRating())
                && isDescriptionValid(model.getDescription());
    }

    boolean isTitleValid(String title) {
        return title != null && title.length() >= INPUT_MIN_SIZE && title.length() <= INPUT_MAX_SIZE;
    }

    boolean isDescriptionValid(String description) {
        return description != null;
    }

    boolean isRatingValid(Integer rating) {
        return rating >= 0;
    }


    boolean isPictureUrlValid(String pictureUrl) {
        return pictureUrl != null;
    }
}

