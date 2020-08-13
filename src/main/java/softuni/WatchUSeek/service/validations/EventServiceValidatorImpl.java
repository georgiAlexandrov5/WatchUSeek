package softuni.WatchUSeek.service.validations;

import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.models.service.EventServiceModel;
import softuni.WatchUSeek.data.models.service.ReviewServiceModel;

import java.time.LocalDate;

import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MAX_SIZE;
import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MIN_SIZE;

@Service
public class EventServiceValidatorImpl implements EventServiceValidator {
    @Override
    public boolean isValid(EventServiceModel model) {
        return isTitleValid(model.getTitle()) && isLocationValid(model.getLocation())
                && isDescriptionValid(model.getDescription()) && isDateValid(model.getDate());
    }

    boolean isTitleValid(String title) {
        return title != null && title.length() >= INPUT_MIN_SIZE && title.length() <= INPUT_MAX_SIZE;
    }

    boolean isDescriptionValid(String description) {
        return description != null;
    }
    boolean isLocationValid(String location) {
        return location != null;
    }


    boolean isDateValid(LocalDate date){
        return date!=null;
    }
}
