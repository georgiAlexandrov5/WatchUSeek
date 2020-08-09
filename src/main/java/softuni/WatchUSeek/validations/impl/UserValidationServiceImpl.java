package softuni.WatchUSeek.validations.impl;

import org.springframework.stereotype.Component;
import softuni.WatchUSeek.data.models.service.UserServiceModel;
import softuni.WatchUSeek.validations.UserValidationService;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isUserValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }
}
