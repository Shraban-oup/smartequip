package com.smartequip.exceptionhandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.smartequip.common.CommonConstants;
import com.smartequip.model.ErrorMessage;

/**
 * This is centralException handler.
 * 
 * @author Shraban.Rana
 *
 */
@ControllerAdvice
public class CentralExceptionHandler  extends ResponseEntityExceptionHandler{

	/**
	 * All type of validation exception handled here
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorMessage> validationException(ValidationException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
				LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.DD_MM_YYYY_HH_MM_SS)), ex.getMessage());

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ErrorMessage> tokenExceptionHandler(TokenException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(),
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
				CommonConstants.TOKEN_CREATION_ERROR_MESSAGE);

		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Parent exception handler
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(),
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
				CommonConstants.INTERNAL_SERVER_ERROR_MESSAGE);

		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
