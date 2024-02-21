package edu.tomerbu.blogfinalproject2024.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PaginationException extends BlogException{
    public PaginationException(String message) {
        super(message);
    }
}
//page=0 =>totalPages