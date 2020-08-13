package softuni.WatchUSeek.validations.watch;

import org.springframework.validation.Errors;
import softuni.WatchUSeek.data.models.binding.WatchCreateBindingModel;
import softuni.WatchUSeek.validations.annotation.Validator;

import static softuni.WatchUSeek.service.validations.ValidationConstants.*;

@Validator
public class WatchAddValidator implements org.springframework.validation.Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return WatchCreateBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        WatchCreateBindingModel watchCreateBindingModel = (WatchCreateBindingModel) o;

        if (watchCreateBindingModel.getManufacturer() == null
                || watchCreateBindingModel.getManufacturer().isEmpty()) {
            errors.rejectValue(
                    "manufacturer", (MANUFACTURER_CAN_NOT_BE_EMPTY), (MANUFACTURER_CAN_NOT_BE_EMPTY)
            );
        }
        if (watchCreateBindingModel.getDescription() == null
                || watchCreateBindingModel.getDescription().isEmpty()) {
            errors.rejectValue(
                    "description", (DESCRIPTION_CAN_NOT_BE_EMPTY), (DESCRIPTION_CAN_NOT_BE_EMPTY)
            );
        }
        if (watchCreateBindingModel.getRefNumber() == null
                || watchCreateBindingModel.getRefNumber().isEmpty()) {
            errors.rejectValue("refNumber", REFERENCE_CAN_NOT_BE_EMPTY, REFERENCE_CAN_NOT_BE_EMPTY);
        }
        if (watchCreateBindingModel.getPicUrl() == null
                || watchCreateBindingModel.getPicUrl().isEmpty()) {
            errors.rejectValue(
                    "picUrl", PICTURE_CAN_NOT_BE_EMPTY, PICTURE_CAN_NOT_BE_EMPTY);
        }
        if (watchCreateBindingModel.getGender() == null
                || watchCreateBindingModel.getGender().isEmpty()) {
            errors.rejectValue("gender", GENDER_CAN_NOT_BE_EMPTY, GENDER_CAN_NOT_BE_EMPTY);
        }
        if (watchCreateBindingModel.getPrice() <= 0) {
            errors.rejectValue("price", PRICE_CAN_NOT_BE_EMPTY, PRICE_CAN_NOT_BE_EMPTY);
        }
        if (watchCreateBindingModel.getContactNumber() == null
                || watchCreateBindingModel.getContactNumber().isEmpty()) {
            errors.rejectValue("contactNumber", CONTACT_CAN_NOT_BE_EMPTY, CONTACT_CAN_NOT_BE_EMPTY);
        }
        if (watchCreateBindingModel.getYear() <= 1850
                || watchCreateBindingModel.getYear() > 2020) {
            errors.rejectValue("year", INVALID_YEAR, INVALID_YEAR);
        }

        if (errors.hasErrors()) {
            return;
        }

    }
}
