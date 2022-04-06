package br.com.junitmockito.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.junitmockito.api.domain.User;
import br.com.junitmockito.api.domain.dto.UserDTO;
import br.com.junitmockito.api.repository.UserRepository;
import br.com.junitmockito.api.service.UserService;
import br.com.junitmockito.api.service.exceptions.DataIntegrationViolationException;
import br.com.junitmockito.api.service.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	
	public User findById(Integer id) {
		return repository.findById(id).orElseThrow( () -> new ObjectNotFoundException(String.format(ObjectNotFoundException.USER_NOT_FOUND,id)));
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User create(User user) {
		emailUserAlreadyExists(user);
		return repository.save(user);
	}

	private void emailUserAlreadyExists(User user) {
		Optional<User> response = repository.findByEmail(user.getEmail());
		if (response.isPresent() && !response.get().getId().equals(user.getId())) {
			throw new DataIntegrationViolationException();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User update(User user) {
		emailUserAlreadyExists(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);		
	}
	
	public User findByEmail(UserDTO dto) {
		return repository.findByEmail(dto.getEmail()).orElse(null);
	}
	
}
