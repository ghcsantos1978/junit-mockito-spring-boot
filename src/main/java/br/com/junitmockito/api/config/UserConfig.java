package br.com.junitmockito.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.junitmockito.api.domain.User;
import br.com.junitmockito.api.repository.UserRepository;

@Configuration
@Profile("local")
public class UserConfig {

	@Autowired
	private UserRepository repository;
	
	
	@Bean
	public void startDB() {
		User user1 = new User(null,"gustavo","gustavo@gmail.com","123");
		User user2 = new User(null,"gustavo","glaucia@gmail.com","123");
		
		repository.saveAll(List.of(user1, user2));
	}
	
	
}
