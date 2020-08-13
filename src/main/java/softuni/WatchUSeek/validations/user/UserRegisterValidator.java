package softuni.WatchUSeek.validations.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import softuni.WatchUSeek.data.models.binding.UserRegisterBindingModel;
import softuni.WatchUSeek.repositories.UserRepository;


@softuni.WatchUSeek.validations.annotation.Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;

        if (userRegisterBindingModel.getUsername() == null ||
                userRegisterBindingModel.getUsername().isEmpty()) {
            errors.rejectValue(
                    "username",
                    UserValidationConstants.USERNAME_CAN_NOT_BE_EMPTY,
                    UserValidationConstants.USERNAME_CAN_NOT_BE_EMPTY
            );
        }
        if (userRegisterBindingModel.getPassword() == null ||
                userRegisterBindingModel.getPassword().isEmpty()) {
            errors.rejectValue(
                    "password",
                    UserValidationConstants.PASSWORD_CANT_BE_EMPTY,
                    UserValidationConstants.PASSWORD_CANT_BE_EMPTY
            );
        }
        if (userRegisterBindingModel.getEmail() == null ||
                userRegisterBindingModel.getEmail().isEmpty()) {
            errors.rejectValue(
                    "email",
                    UserValidationConstants.EMAIL_CANT_BE_EMPTY,
                    UserValidationConstants.EMAIL_CANT_BE_EMPTY
            );
        }
        if (errors.hasErrors()){
            return;
        }


        if (this.userRepository.findByUsername(userRegisterBindingModel.getUsername()).isPresent()) {
            errors.rejectValue(
                    "username",
                    String.format(UserValidationConstants.USERNAME_ALREADY_EXISTS, userRegisterBindingModel.getUsername()),
                    String.format(UserValidationConstants.USERNAME_ALREADY_EXISTS, userRegisterBindingModel.getUsername())
            );
        }

        if (userRegisterBindingModel.getUsername().length() < UserValidationConstants.USERNAME_MIN_LENGTH ||
                userRegisterBindingModel.getUsername().length() > UserValidationConstants.USERNAME_MAX_LENGTH) {
            errors.rejectValue(
                    "username",
                    String.format(UserValidationConstants.USERNAME_LENGTH, UserValidationConstants.USERNAME_MIN_LENGTH,
                            UserValidationConstants.USERNAME_MAX_LENGTH),
                    String.format(UserValidationConstants.USERNAME_LENGTH, UserValidationConstants.USERNAME_MIN_LENGTH,
                            UserValidationConstants.USERNAME_MAX_LENGTH)
            );
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            errors.rejectValue(
                    "password",
                    UserValidationConstants.PASSWORDS_DO_NOT_MATCH,
                    UserValidationConstants.PASSWORDS_DO_NOT_MATCH
            );
        }

        if (this.userRepository.findByEmail(userRegisterBindingModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(UserValidationConstants.EMAIL_ALREADY_EXISTS, userRegisterBindingModel.getEmail()),
                    String.format(UserValidationConstants.EMAIL_ALREADY_EXISTS, userRegisterBindingModel.getEmail())
            );
        }
    }
}
