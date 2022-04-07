package br.com.junitmockito.api.service.exceptions;

public class DataIntegrationViolationException extends RuntimeException {

	/**
	 * 
	 */
	
	static final String MESSAGE = "Email já cadastrado";
	
	private static final long serialVersionUID = 1L;
	
	public DataIntegrationViolationException() {
		super(MESSAGE);
	}

	public DataIntegrationViolationException(String message) {
		super(message);
	}
	
	

}
