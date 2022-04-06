package br.com.junitmockito.api.service.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String USER_NOT_FOUND = "Usuario %s não encontrado";
	
	public ObjectNotFoundException(String message) {
		super(message);
	}
	
}
