package br.com.junitmockito.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.junitmockito.api.domain.User;
import br.com.junitmockito.api.domain.dto.UserDTO;
import br.com.junitmockito.api.repository.UserRepository;
import br.com.junitmockito.api.service.exceptions.DataIntegrationViolationException;
import br.com.junitmockito.api.service.exceptions.ObjectNotFoundException;

@SpringBootTest
class UserServiceImplTest {
	
	private static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";

	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

	private static final String PASSWORD = "123";

	private static final String EMAIL = "gustavo@gmail.com";

	private static final String NAME = "gustavo";

	private static final int ID = 1;

	@InjectMocks
	private UserServiceImpl service;	
	
	@Mock
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	private User user;

	private UserDTO userDTO;
	
	private Optional<User> userOptional;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	
	@Test
	void whenFindByIdReturnAnUserInstance() {
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(userOptional);
		User response = service.findById(ID);
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());

	}

	@Test
	void whenFindByIdReturnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
		try {
			service.findById(anyInt());
		}
		catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());
		}

	}
	
	
	
	@Test
	void testFindAll() {
		when(repository.findAll()).thenReturn(List.of(user));
		List<User> response = service.findAll();
		assertNotNull(response);
		assertTrue(!response.isEmpty());
		assertEquals(Integer.valueOf(1), response.size());
		
	}

	@Test
	void whenCreateThenReturnSucess() {
		when(repository.save(Mockito.any())).thenReturn(user);
		User response = service.create(user);
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(EMAIL,response.getEmail());
	}

	@Test
	void whenCreateThenReturnDataIntegratyViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(userOptional);
		try {
			userOptional.get().setId(Integer.valueOf(2));
			service.create(user);
		}
		catch(Exception ex) {
			assertEquals(DataIntegrationViolationException.class, ex.getClass());
			assertEquals(EMAIL_JA_CADASTRADO,ex.getMessage());
		}

	}
	
	@Test
	void whenUpdateThenReturnSucess() {
		when(repository.save(Mockito.any())).thenReturn(user);
		User response = service.update(user);
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(EMAIL,response.getEmail());
	}

	
	@Test
	void whenUpdateThenReturnDataIntegratyViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(userOptional);
		try {
			userOptional.get().setId(Integer.valueOf(2));
			service.create(user);
		}
		catch(Exception ex) {
			assertEquals(DataIntegrationViolationException.class, ex.getClass());
			assertEquals(EMAIL_JA_CADASTRADO,ex.getMessage());
		}

	}
	


	@Test
	void whenDeleteThenReturnSucess() {
		when(repository.findById(anyInt())).thenReturn(userOptional);
		doNothing().when(repository).deleteById(anyInt());
		service.delete(anyInt());
		verify(repository,times(1)).deleteById(anyInt());
	}

	@Test
	void whenDeleteThenReturnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
		try {
			service.delete(anyInt());
		}
		catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());

		}
	}
	
	
	private void startUser() {
		this.user = new User(ID,NAME,EMAIL,PASSWORD);
		this.userDTO = new UserDTO(ID,NAME,EMAIL,PASSWORD);
		this.userOptional = Optional.of(new User(ID,NAME,EMAIL,PASSWORD));
	}
	
}
