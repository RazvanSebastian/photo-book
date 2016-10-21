package com.security;

import java.util.Date;
import javax.servlet.Filter;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.repository.model.User;
import com.repository.model.UserRole;
import com.repository.repository.UserRepository;

@EnableAutoConfiguration
@Configuration
@ComponentScan

public class StatelessAuthentication {

	@Bean
	public InitializingBean insertDefaultUsers() {
		return new InitializingBean() {
			@Autowired
			private UserRepository userRepository;

			@Override
			public void afterPropertiesSet() {
				if (userRepository.findByEmail("admin") == null)
					addUser("admin", "admin");
				if (userRepository.findByEmail("user") == null)
					addUser("user", "user");
			}

			private void addUser(String username, String password) {
				User user = new User();
				user.setBirthDay(new Date());
				user.setFirstName("razvan");
				user.setLastName("sebastian");
				user.setEmail(username);
				user.setUsername(username);
				user.setPassword(new BCryptPasswordEncoder().encode(password));
				user.grantRole(UserRole.ADMIN);
				user.grantRole(UserRole.USER);
				userRepository.save(user);
			}
		};
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
}
