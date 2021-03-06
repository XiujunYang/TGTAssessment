package com.tgt.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid userId")
public class InvalidUserIdException extends RuntimeException {
	private static final long serialVersionUID = -3042135041598059789L;

}
