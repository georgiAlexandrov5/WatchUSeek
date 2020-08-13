package softuni.WatchUSeek.validations.event;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import softuni.WatchUSeek.data.models.binding.EventCreateBindingModel;

import java.time.LocalDate;

import static softuni.WatchUSeek.service.validations.ValidationConstants.*;

@softuni.WatchUSeek.validations.annotation.Validator
public class EventAddValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return EventCreateBindingModel.class.equals(aClass);    }

    @Override
    public void validate(Object o, Errors errors) {
        EventCreateBindingModel eventCreateBindingModel = (EventCreateBindingModel) o;

        if (eventCreateBindingModel.getTitle() == null
                || eventCreateBindingModel.getTitle().isEmpty()) {
            errors.rejectValue(
                    "title", (TITLE_CAN_NOT_BE_EMPTY), (TITLE_CAN_NOT_BE_EMPTY)
            );
        }
        if (eventCreateBindingModel.getDescription() == null
                || eventCreateBindingModel.getDescription().isEmpty()) {
            errors.rejectValue(
                    "description", (DESCRIPTION_CAN_NOT_BE_EMPTY), (DESCRIPTION_CAN_NOT_BE_EMPTY)
            );
        }
        if (eventCreateBindingModel.getLocation() == null
                || eventCreateBindingModel.getLocation().isEmpty()) {
            errors.rejectValue(
                    "location", (LOCATION_CAN_NOT_BE_EMPTY), (LOCATION_CAN_NOT_BE_EMPTY)
            );
        }

        if (eventCreateBindingModel.getDate() == null) {
            errors.rejectValue(
                    "date", (DATE_CAN_NOT_BE_EMPTY), (DATE_CAN_NOT_BE_EMPTY)
            );
        }

        if (eventCreateBindingModel.getDate().isBefore(LocalDate.now())){
            errors.rejectValue(
                    "date", (INVALID_DATE), (INVALID_DATE)
            );
        }

        if (errors.hasErrors()) {
            return;
        }

    }
}
