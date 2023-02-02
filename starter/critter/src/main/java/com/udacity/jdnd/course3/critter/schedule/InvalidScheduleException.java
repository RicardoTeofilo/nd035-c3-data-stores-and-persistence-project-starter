package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid Schedule data")
public class InvalidScheduleException extends RuntimeException{
    public InvalidScheduleException() {
    }

    public InvalidScheduleException(String message) {
        super(message);
    }
}
