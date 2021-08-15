package com.tgt.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid userId or empty request in post, email and sms field")
public class InvalidUpdateRequestException extends RuntimeException {
	private static final long serialVersionUID = -6719242483501730001L;
}
