package br.com.junitmockito.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.junitmockito.api.domain.User;
import br.com.junitmockito.api.domain.dto.UserDTO;
import br.com.junitmockito.api.service.UserService;

@RestController
@RequestMapping(value="user")
public class UserResource {

	@Autowired
	private UserService service;

	@Autowired
	private ModelMapper mapper;



	@GetMapping(value= "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok().body(mapper.map(service.findById(id),UserDTO.class));
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll(){
		return new ResponseEntity<List<UserDTO>>(service.findAll().
				stream().map(x -> mapper.map(x, UserDTO.class)).
				collect(Collectors.toList()),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto){
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(service.create(mapper.map(dto, User.class)).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO dto){
		dto.setId(id);
		User response = service.update(mapper.map(dto,User.class));
		return ResponseEntity.ok().body(mapper.map(response, UserDTO.class));
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable Integer id){
		findById(id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
