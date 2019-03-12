package me.hardlearner.roomrentalmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Wrong event id")
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Wrong id");
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}
