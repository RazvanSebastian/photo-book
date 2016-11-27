package com.restApi;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

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
import com.service.local.AES;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.weaver.NewParentTypeMunger;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	
	@Autowired
	private UserDetailsService userService;
	@Autowired
	private AES aesService;
	

	
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
	
	@RequestMapping(value="/api/account/change-password",method=  {RequestMethod.PUT,RequestMethod.GET})
	@ResponseBody
	public String changePassword(
			@RequestHeader("required") boolean required,
			@RequestHeader(value="UserName",required=false) String username,
			@RequestHeader(value="OldPassword",required=false) String oldPassword,
			@RequestHeader(value="NewPassword",required=false) String newPassword,
			@RequestHeader(value="IV",required=false) String ivBase64Client,
			HttpServletRequest request
			){
		
		if(request.getMethod().equals("GET")){
			return this.aesService.convertToBase64(this.aesService.generateRandomIv16Byte());
		}
		if(request.getMethod().equals("PUT")){
				String decryptedNewPassword=this.aesService.decrypt("0123456789abcdef","yYJ2ThDp053TALRw" ,newPassword );
				String decryptedOldPassword=this.aesService.decrypt("0123456789abcdef", "yYJ2ThDp053TALRw", oldPassword);
				try {
					this.userService.updatePassword(decryptedOldPassword, decryptedNewPassword, username);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.getMessage();
				}
		}
		return null;
		
		
	}
}
