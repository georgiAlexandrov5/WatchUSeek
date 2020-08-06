package softuni.WatchUSeek.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Strap doesn't exist!")
public class StrapNotFoundException extends RuntimeException {

    private int status;

    public StrapNotFoundException() {
        this.status = 404;
    }

    public StrapNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
