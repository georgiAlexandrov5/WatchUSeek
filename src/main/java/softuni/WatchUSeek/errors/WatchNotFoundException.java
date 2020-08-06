package softuni.WatchUSeek.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Watch doesn't exist!")
public class WatchNotFoundException extends RuntimeException{
    private int status;

    public WatchNotFoundException() {
        this.status = 404;
    }

    public WatchNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }

}

