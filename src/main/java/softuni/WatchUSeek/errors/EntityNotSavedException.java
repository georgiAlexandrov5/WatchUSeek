package softuni.WatchUSeek.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Wrong input")

public class EntityNotSavedException extends RuntimeException{
    public EntityNotSavedException() {

    }

    public EntityNotSavedException(String message) {
        super(message);

    }
}

