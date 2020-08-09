package softuni.WatchUSeek.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Event doesn't exist!")
public class EventNotFoundException extends RuntimeException {
    private int status;

    public EventNotFoundException() {
        this.status = 404;
    }

    public EventNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
