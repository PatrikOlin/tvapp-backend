package com.tvapp.utils.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExistAdvice {
    @ResponseBody
    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String invalidUserHandler(UserExistException ex) {
        return ex.getMessage();
    }
}
