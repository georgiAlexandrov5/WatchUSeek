package softuni.WatchUSeek.service.validations;

import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.models.service.StrapServiceModel;

import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MAX_SIZE;
import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MIN_SIZE;

@Service
public class StrapServiceModelValidatorImpl implements StrapServiceModelValidator {
    @Override
    public boolean isValid(StrapServiceModel model) {
        return isNameValid(model.getName()) && isMaterialValid(model.getMaterial())
                && isDescriptionValid(model.getDescription()) && isPhoneValid(model.getPhone())
                && isPictureUrlValid(model.getPicUrl()) && isPriceValid(model.getPrice());
    }

    boolean isNameValid(String name) {
        return name != null && name.length() >= INPUT_MIN_SIZE && name.length() <= INPUT_MAX_SIZE;
    }

    boolean isDescriptionValid(String description) {
        return description != null;
    }

    boolean isPriceValid(Double price) {
        return price >= 0;
    }

    boolean isMaterialValid(String material) {
        return material != null && material.length() >= INPUT_MIN_SIZE && material.length() <= INPUT_MAX_SIZE;
    }

    boolean isPhoneValid(String phone) {
        return phone != null;
    }

    boolean isPictureUrlValid(String pictureUrl) {
        return pictureUrl != null;
    }

}
