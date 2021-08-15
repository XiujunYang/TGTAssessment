package com.tgt.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="UserId does not exist")
public class UserIdNotExistException extends RuntimeException {
}
