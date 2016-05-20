package com.elite.controller.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found")
public class EntityObjectNotFoundException extends RuntimeException {
    public EntityObjectNotFoundException(long id) {
        super("Object with id '" + id + "' not found");
    }
}
