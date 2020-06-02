package br.com.erudio.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.erudio.exception.ExceptionResponse;
import br.com.erudio.exception.InvalidJwtAuthtenticationException;
import br.com.erudio.exception.ResourceNotFoundException;

@ControllerAdvice
@RestController

public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest req) {
		ExceptionResponse excepResponse = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(excepResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest req) {
		ExceptionResponse excepResponse = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(excepResponse, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(InvalidJwtAuthtenticationException.class)
	public final ResponseEntity<ExceptionResponse> InvalidJwtAuthtenticationException(Exception ex, WebRequest req) {
		ExceptionResponse excepResponse = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(excepResponse, HttpStatus.BAD_REQUEST);

	}
}
