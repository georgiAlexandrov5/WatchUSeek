package softuni.WatchUSeek.validations.review;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import softuni.WatchUSeek.data.models.binding.ReviewCreateBindingModel;

import static softuni.WatchUSeek.service.validations.ValidationConstants.*;

@softuni.WatchUSeek.validations.annotation.Validator
public class ReviewAddValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ReviewCreateBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ReviewCreateBindingModel reviewCreateBindingModel = (ReviewCreateBindingModel) o;

        if (reviewCreateBindingModel.getTitle() == null
                || reviewCreateBindingModel.getTitle().isEmpty()) {
            errors.rejectValue(
                    "title", (TITLE_CAN_NOT_BE_EMPTY), (TITLE_CAN_NOT_BE_EMPTY)
            );
        }
        if (reviewCreateBindingModel.getDescription() == null
                || reviewCreateBindingModel.getDescription().isEmpty()) {
            errors.rejectValue(
                    "description", (DESCRIPTION_CAN_NOT_BE_EMPTY), (DESCRIPTION_CAN_NOT_BE_EMPTY)
            );
        }
        if (reviewCreateBindingModel.getPictureUrl() == null
                || reviewCreateBindingModel.getPictureUrl().isEmpty()) {
            errors.rejectValue(
                    "pictureUrl", PICTURE_CAN_NOT_BE_EMPTY, PICTURE_CAN_NOT_BE_EMPTY);
        }

        if (reviewCreateBindingModel.getRating() < 0) {
            errors.rejectValue("rating", RATING_CAN_NOT_BE_EMPTY, RATING_CAN_NOT_BE_EMPTY);
        }


        if (errors.hasErrors()) {
            return;
        }

    }
}
