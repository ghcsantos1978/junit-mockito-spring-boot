package br.com.junitmockito.api.service;

import java.util.List;

import br.com.junitmockito.api.domain.User;
import br.com.junitmockito.api.domain.dto.UserDTO;

public interface UserService {

	
	User findById(Integer id);

	List<User> findAll();

	User create(User user);

	User update(User user);

	void delete(Integer id);
}