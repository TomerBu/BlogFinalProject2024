package edu.tomerbu.blogfinalproject2024.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String entityName, String property, Object value) {

        super("Entity %s with %s=%s Not Found".formatted(entityName, property, value));
    }
}
