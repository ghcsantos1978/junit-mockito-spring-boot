package br.com.junitmockito.api.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.junitmockito.api.resources.exceptions.ResourceExceptionHandler;
import br.com.junitmockito.api.resources.exceptions.StandardError;
import br.com.junitmockito.api.service.exceptions.DataIntegrationViolationException;
import br.com.junitmockito.api.service.exceptions.ObjectNotFoundException;

@SpringBootTest
class ResourceExceptionHandlerTest {

	private static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";
	@InjectMocks
	private ResourceExceptionHandler resourceExceptionHandler;
	
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void when_OjectNotFound_Then_Return_Response_Entity_With_Status_Error() {
		ResponseEntity<StandardError> response = resourceExceptionHandler.
				objectNotFound(new ObjectNotFoundException("Objeto não encontrado"),new MockHttpServletRequest());
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getBody().getClass(), StandardError.class);
		assertEquals(response.getBody().getError(), "Objeto não encontrado");

	}

	@Test
	void when_Data_Integration_Violation_Exception_Then_Return_Response_Entity_With_Status_Error() {
		ResponseEntity<StandardError> response = resourceExceptionHandler.
				dataIntegrationViolationException(new DataIntegrationViolationException(EMAIL_JA_CADASTRADO),new MockHttpServletRequest());
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody().getClass(), StandardError.class);
		assertEquals(response.getBody().getError(), EMAIL_JA_CADASTRADO);
	}

}
