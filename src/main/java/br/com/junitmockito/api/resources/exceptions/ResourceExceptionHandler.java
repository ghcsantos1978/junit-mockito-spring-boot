package br.com.junitmockito.api.resources.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.junitmockito.api.service.exceptions.DataIntegrationViolationException;
import br.com.junitmockito.api.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex,HttpServletRequest request){
		StandardError error = new StandardError(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),ex.getMessage(),request.getRequestURI());
		return new ResponseEntity<StandardError>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrationViolationException.class)
	public ResponseEntity<StandardError> dataIntegrationViolationException(DataIntegrationViolationException ex,HttpServletRequest request){
		StandardError error = new StandardError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),ex.getMessage(),request.getRequestURI());
		return new ResponseEntity<StandardError>(error,HttpStatus.BAD_REQUEST);
	}
	
}
