package softuni.WatchUSeek.validations.strap;

import org.springframework.validation.Errors;
import softuni.WatchUSeek.data.models.binding.StrapCreateBindingModel;
import softuni.WatchUSeek.validations.annotation.Validator;

import static softuni.WatchUSeek.service.validations.ValidationConstants.*;
import static softuni.WatchUSeek.service.validations.ValidationConstants.RATING_CAN_NOT_BE_EMPTY;

@Validator
public class StrapAddValidator implements org.springframework.validation.Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return StrapCreateBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        StrapCreateBindingModel strapCreateBindingModel = (StrapCreateBindingModel) o;

        if (strapCreateBindingModel.getName() == null
                || strapCreateBindingModel.getName().isEmpty()) {
            errors.rejectValue(
                    "name", (NAME_CAN_NOT_BE_EMPTY), (NAME_CAN_NOT_BE_EMPTY)
            );
        }
        if (strapCreateBindingModel.getDescription() == null
                || strapCreateBindingModel.getDescription().isEmpty()) {
            errors.rejectValue(
                    "description", (DESCRIPTION_CAN_NOT_BE_EMPTY), (DESCRIPTION_CAN_NOT_BE_EMPTY)
            );
        }
        if (strapCreateBindingModel.getPicUrl() == null
                || strapCreateBindingModel.getPicUrl().isEmpty()) {
            errors.rejectValue(
                    "picUrl", PICTURE_CAN_NOT_BE_EMPTY, PICTURE_CAN_NOT_BE_EMPTY);
        }

        if (strapCreateBindingModel.getMaterial() == null
                || strapCreateBindingModel.getMaterial().isEmpty()) {
            errors.rejectValue("material", MATERIAL_CAN_NOT_BE_EMPTY, MATERIAL_CAN_NOT_BE_EMPTY);
        }


        if (strapCreateBindingModel.getPhone() == null
                || strapCreateBindingModel.getPhone().isEmpty()) {
            errors.rejectValue("phone", CONTACT_CAN_NOT_BE_EMPTY, CONTACT_CAN_NOT_BE_EMPTY);
        }
        if (strapCreateBindingModel.getPrice() <= 0) {
            errors.rejectValue("price", PRICE_CAN_NOT_BE_EMPTY, PRICE_CAN_NOT_BE_EMPTY);
        }


        if (errors.hasErrors()) {
            return;
        }

    }
}
