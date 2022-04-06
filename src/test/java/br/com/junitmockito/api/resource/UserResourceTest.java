package br.com.junitmockito.api.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.junitmockito.api.domain.User;
import br.com.junitmockito.api.domain.dto.UserDTO;
import br.com.junitmockito.api.resources.UserResource;
import br.com.junitmockito.api.service.UserService;

@SpringBootTest
class UserResourceTest {

	private static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";

	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

	private static final String PASSWORD = "123";

	private static final String EMAIL = "gustavo@gmail.com";

	private static final String NAME = "gustavo";

	private static final int ID = 1;
	
	
	@InjectMocks
	private UserResource resource;
	
	@Mock
	private UserService service;

	@Mock
	private ModelMapper mapper;

	private User user;

	private UserDTO userDTO;

	
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void shoulFindByIdReturnSucess() {
		when(service.findById(Mockito.anyInt())).thenReturn(user);
		when(mapper.map(user,UserDTO.class)).thenReturn(userDTO);
		ResponseEntity<UserDTO> response = resource.findById(ID);
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
		Assertions.assertEquals(UserDTO.class, response.getBody().getClass());
		Assertions.assertEquals(ID, response.getBody().getId());
		Assertions.assertEquals(NAME, response.getBody().getName());
		Assertions.assertEquals(EMAIL, response.getBody().getEmail());
		Assertions.assertEquals(PASSWORD, response.getBody().getPassword());


		
	}

	@Test
	void shouldFindAllThenReturnListOfUsersDTO() {
		when(service.findAll()).thenReturn(List.of(user,user));
		when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
		ResponseEntity<List<UserDTO>> response = resource.findAll();
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
		Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
		Assertions.assertEquals(UserDTO.class, response.getBody().get(0).getClass());
		Assertions.assertTrue(response.getBody().size() > 0);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		
	}

	@Test
	void whenCreateThenReturnCreated() {
		when(mapper.map(userDTO, User.class)).thenReturn(user);
		when(service.create(user)).thenReturn(user);
		ResponseEntity<UserDTO> response = resource.create(userDTO);
		assertNotNull(response);
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void shouldUpdateThenReturnSucess() {
		when(service.update(user)).thenReturn(user);
		when(mapper.map(userDTO, User.class)).thenReturn(user);
		when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = resource.update(ID, userDTO);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	void shouldDeleteThenReturnSucess() {
		doNothing().when(service).delete(ID);
		ResponseEntity<UserDTO> response = resource.delete(ID);
		assertNotNull(response);
		verify(service,times(1)).delete(ID);
		assertEquals(ResponseEntity.class,response.getClass());
		assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

	}
	
	private void startUser() {
		this.user = new User(ID,NAME,EMAIL,PASSWORD);
		this.userDTO = new UserDTO(ID,NAME,EMAIL,PASSWORD);
	}
	

}
