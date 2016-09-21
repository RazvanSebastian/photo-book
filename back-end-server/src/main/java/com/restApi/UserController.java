package com.restApi;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.DTO.UserDTO;
import com.repository.model.User;
import com.service.UserDetailsService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	
	@Autowired
	private UserDetailsService userService;
	
	@RequestMapping(value="/api/register",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> registerNewUser(@RequestBody UserDTO user){
		try {
			this.userService.saveUser(user);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/api/login", method=RequestMethod.GET)
	public String send(){
		return "hi!";
	}
}
