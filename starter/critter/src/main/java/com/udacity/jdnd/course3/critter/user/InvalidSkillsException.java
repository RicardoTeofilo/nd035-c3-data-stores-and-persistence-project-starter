package com.udacity.jdnd.course3.critter.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid Skill Set")
public class InvalidSkillsException extends RuntimeException{
    public InvalidSkillsException() {
    }

    public InvalidSkillsException(String message) {
        super(message);
    }

}
