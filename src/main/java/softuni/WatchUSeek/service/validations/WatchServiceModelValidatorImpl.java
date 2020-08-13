package softuni.WatchUSeek.service.validations;

import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.models.service.WatchServiceModel;

import java.math.BigDecimal;

import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MAX_SIZE;
import static softuni.WatchUSeek.service.validations.ValidationConstants.INPUT_MIN_SIZE;

@Service
public class WatchServiceModelValidatorImpl implements WatchServiceModelValidator {
    @Override
    public boolean isValid(WatchServiceModel model) {
        return isManufacturerValid(model.getManufacturer()) && isRefNumberValid(model.getRefNumber())
                && isDescriptionValid(model.getDescription()) && isContactNumberValid(model.getContactNumber())
                && isGenderValid(model.getGender()) && isPictureUrlValid(model.getPicUrl())
                && isPriceValid(model.getPrice()) && isYearValid(model.getYear());
    }

    boolean isManufacturerValid(String name) {
        return name != null && name.length() >= INPUT_MIN_SIZE && name.length() <= INPUT_MAX_SIZE;
    }

    boolean isDescriptionValid(String description) {
        return description != null;
    }

    boolean isPriceValid(Double price) {
        return price >= 0;
    }

    boolean isRefNumberValid(String refNumber) {
        return refNumber != null && refNumber.length() >= INPUT_MIN_SIZE && refNumber.length() <= INPUT_MAX_SIZE;
    }

    boolean isContactNumberValid(String contactNumber) {
        return contactNumber != null;
    }

    boolean isYearValid(int year) {
        return year >= 1850 && year <= 2020;
    }

    boolean isPictureUrlValid(String pictureUrl) {
        return pictureUrl != null;
    }

    boolean isGenderValid(String gender) {
        return gender != null;
    }
}
