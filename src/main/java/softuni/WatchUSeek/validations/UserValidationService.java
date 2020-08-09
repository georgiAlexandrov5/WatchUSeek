package softuni.WatchUSeek.validations;

import softuni.WatchUSeek.data.models.service.UserServiceModel;

public interface UserValidationService {
    boolean isUserValid(UserServiceModel userServiceModel);

}
