package softuni.WatchUSeek.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User name is not free")

public class UserNameNotFreeException extends RuntimeException {

    public UserNameNotFreeException() {
    }

    public UserNameNotFreeException(String message) {
        super(message);
    }
}
