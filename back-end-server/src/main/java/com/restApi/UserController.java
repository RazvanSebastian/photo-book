package com.restApi;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.DTO.UserDTO;
import com.repository.model.User;
import com.service.UserDetailsService;
import com.service.local.Crypto;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	
	@Autowired
	private UserDetailsService userService;
	@Autowired
	private Crypto cryptoHmac;
	
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
	
	@RequestMapping(value="/api/login-guard/check",method=RequestMethod.HEAD)
	public ResponseEntity<?> checkUserIdentityLoginGuard(){
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value="/api/account/change-personal-info",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUserInfo(@RequestBody UserDTO userProfile, @RequestHeader("UserName") String username){
		try {
			this.userService.updateProfile(userProfile, username);
			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(value="/api/account/change-password",method=RequestMethod.PUT)
	public ResponseEntity<?> changePassword(
			@RequestHeader("UserName") String username,
			@RequestHeader("NewPassword") String encriptedPassword,
			@RequestHeader("OldPassword") String oldPassword){
				System.out.println(username);
				System.out.println(encriptedPassword);
				System.out.println(oldPassword);
		try {
			System.out.println(this.cryptoHmac.decrypt(encriptedPassword));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.OK);
		
	}
}
