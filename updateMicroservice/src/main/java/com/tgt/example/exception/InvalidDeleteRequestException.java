package com.tgt.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid userId")
public class InvalidDeleteRequestException extends RuntimeException {
}
