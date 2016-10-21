package com.service;

import org.apache.commons.validator.routines.EmailValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.DTO.UserDTO;
import com.repository.model.User;
import com.repository.model.UserRole;
import com.repository.repository.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	private UserRepository userRepoistory;

	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	public void saveUser(UserDTO user) throws Exception {
		User newUser=new User();
		this.validatorInfo(user);
		newUser.setUsername(user.getUsername());
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		newUser.setBirthDay(user.getBirthDay());
		newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		newUser.grantRole(UserRole.USER);
		this.userRepoistory.save(newUser);
	}
	
	/*
	 * Register section of verifications
	 * */
	private void validatorInfo(UserDTO user) throws Exception {
		if (user.getEmail().length() == 0 || user.getFirstName().length() == 0 || user.getLastName().length() == 0
				|| user.getBirthDay() == null)
			throw new Exception("All input are required!");
		if (user.getPassword().length() <= 6)
			throw new Exception("Password must have min 6 characters!");
		if (this.userRepoistory.findByEmail(user.getEmail()) != null)
			throw new Exception("This email is used!");
		if (EmailValidator.getInstance().isValid(user.getEmail()) == false)
			throw new Exception("The email has invalid pattern!");
		if(this.userRepoistory.findByUsername(user.getUsername())!=null)
			throw new Exception("The user name is not valid!");
	}
	
	public User findByEmail(String email){
		return this.userRepoistory.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = this.userRepoistory.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        detailsChecker.check(user);
        return user;
	}
}
