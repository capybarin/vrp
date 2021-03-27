package com.diplom.vrp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyResponseException extends RuntimeException {
    public EmptyResponseException(String str) {super(str);}
}
