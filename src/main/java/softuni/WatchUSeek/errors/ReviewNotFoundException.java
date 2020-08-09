package softuni.WatchUSeek.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Review doesn't exist!")
public class ReviewNotFoundException extends RuntimeException {
    private int status;

    public ReviewNotFoundException() {
        this.status = 404;
    }

    public ReviewNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
