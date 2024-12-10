package com.Files.UserEntity.Controller;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Files.UserEntity.User;
import com.Files.UserEntity.DTOs.ChangePassword;
import com.Files.UserEntity.DTOs.LoginRequestDto;
 import com.Files.UserEntity.DTOs.UserRequsetDTO;
import com.Files.UserEntity.DTOs.UserResponseDTO;
import com.Files.UserEntity.ExceptionHandling.UserNotFoundException;
import com.Files.UserEntity.Service.JwtService;
import com.Files.UserEntity.Service.UserService;

 import jakarta.validation.Valid;
 

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	 

	@GetMapping
	public String UW(@RequestHeader("Authorization") String authHeader) {
		return "Welcome User Please Register First";
	}
	@PostMapping("/register")
	public ResponseEntity<?> regUSer(@RequestBody @Valid UserRequsetDTO userRequsetDTO, BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) {
	            String errorMessage = bindingResult.getAllErrors().stream()
	                                               .map(error -> error.getDefaultMessage())
	                                               .reduce((msg1, msg2) -> msg1 + ", " + msg2)
	                                               .orElse("Validation error");
	            return new ResponseEntity<>(errorMessage, org.springframework.http.HttpStatus.BAD_GATEWAY);
	        }

		  UserResponseDTO register = userService.register(userRequsetDTO);
	        return new ResponseEntity<>(register, org.springframework.http.HttpStatus.OK);
 
	}


//	@PostMapping("/login")
//	public String generateToken(@RequestParam String username, @RequestParam String password) {
//		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
//		if(authenticate.isAuthenticated()) {	
//			System.out.println(jwtService.generateToken(username));
//			return jwtService.generateToken(username);
//		}else {
//			throw new RuntimeException("Invalid user");
//		}
//	}
	
	@PostMapping("/login")
	public String generateToken(@RequestBody LoginRequestDto loginRequestDto) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getUser_password()));
		if(authenticate.isAuthenticated()) {	
 			return jwtService.generateToken(loginRequestDto.getUsername());
		}else {
			throw new RuntimeException("Invalid user");
		}
	}

	@GetMapping("/getUser")
	public User getUserByToken(@RequestHeader("Authorization") String authHeader) throws Exception {
		System.out.println(authHeader);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new Exception();
		}

		try {
			String token = authHeader.substring(7); 
			jwtService.validateToken(token);   

			String username = jwtService.extractUsername(token);  
			if (username == null) {
				throw new Exception();
			}

			User user = userService.getUserByUsername(username);   
			if (user == null) {
				throw new Exception();
			}

			System.out.println("User Object for order: " +user);

			return user;

		} catch (Exception e) {
			throw new Exception();

		}
	}
	
	 



	@GetMapping("/validate")
	public String validateToken(@RequestParam String token) {
		jwtService.validateToken(token);
		return "Token is Valid ";
	}

	@GetMapping("/all")
	public List<UserResponseDTO> showall(@RequestHeader(value = "Authorization")String authHeader) {
		return userService.showall();
	}

	@GetMapping("/{id}")
	public UserResponseDTO showUser(@RequestHeader(value = "Authorization")String authHeader,@PathVariable long id) throws UserNotFoundException {
		return userService.showuser(id);

	}
	
	
	@PutMapping("/update")
	public UserResponseDTO update(@RequestHeader("Authorization") String authHeader,@RequestBody UserRequsetDTO userRequsetDTO) throws Exception {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new Exception();
		}
		 
			String token = authHeader.substring(7); 
			jwtService.validateToken(token);   
                     String userName = jwtService.extractUsername(token);
 			            
		return userService.update(userName,userRequsetDTO);
	}

	@PutMapping("/resetPassword")
	public String updatePassword(@RequestHeader("Authorization") String authHeader,@RequestBody ChangePassword changePassword) throws Exception {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new Exception();
		}
		 
			String token = authHeader.substring(7); 
			jwtService.validateToken(token);   
                     String userName = jwtService.extractUsername(token);
		userService.updatePassword(userName,changePassword);
		return "Password Changed";
	}



	@DeleteMapping("/delete")
	public String delete(@RequestHeader("Authorization") String authHeader) throws Exception {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new Exception("User not found");
		}
		 
			String token = authHeader.substring(7); 
			jwtService.validateToken(token);   
                     String userName = jwtService.extractUsername(token);
		return userService.deleteUser(userName);
	}


	@GetMapping("get/{id}")
	public User getUser(@PathVariable long id) throws UserNotFoundException {
		return userService.getUser(id);
	}

}
