 package com.Files.UserEntity.Service;

 import java.time.LocalDateTime;
import java.util.List;
 import java.util.stream.Collectors;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Files.UserEntity.User;
import com.Files.UserEntity.DTOs.ChangePassword;
import com.Files.UserEntity.DTOs.UserRequsetDTO;
import com.Files.UserEntity.DTOs.UserResponseDTO;
import com.Files.UserEntity.ExceptionHandling.UserNotFoundException;
import com.Files.UserEntity.JMS.EmailSenderServices;
import com.Files.UserEntity.Mapper.Mapper;
import com.Files.UserEntity.Repos.UserRepo;

import jakarta.transaction.Transactional;

 
@Service
public class UserService {
	
	 @Autowired
	 PasswordEncoder passwordEncoder;
	
	 @Autowired
	 private EmailSenderServices emailSenderServices;
	
     @Autowired
	 private UserRepo userRepo;
     
	 @Autowired
     private Mapper mapper;
     
 
	public UserResponseDTO register(UserRequsetDTO userRequsetDTO) {
	 userRequsetDTO.setUser_password(passwordEncoder.encode(userRequsetDTO.getUser_password()));
	 User user = mapper.dtoTOUser(userRequsetDTO);
	 User save = userRepo.save(user);
	 emailSenderServices.sendMail(save.getUser_email(), "Registration confirmation", "Dear user successfully registered for Book store application", save);
	return  mapper.userToDto(save);
	 
		
	}

	public List<UserResponseDTO> showall() {		 
		  List<User> all = userRepo.findAll();
 		  return all.stream().map(mapper::userToDto).collect(Collectors.toList());
	}
 

	public UserResponseDTO showuser(Long id) throws UserNotFoundException {
		  User user = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User not foud for id: "+id));
		  
		  return mapper.userToDto(user);
		
	}
	
	
	public UserResponseDTO update(String id,UserRequsetDTO userRequsetDTO) throws Exception {
		User user = userRepo.findByUsername(id);
		if(user!=null) {
		
		 userRequsetDTO.setUser_password(passwordEncoder.encode(userRequsetDTO.getUser_password()));

		user.setUsername(userRequsetDTO.getUsername());
		user.setF_name(userRequsetDTO.getF_name());
		user.setL_name(userRequsetDTO.getL_name());
 		user.setUser_dob(userRequsetDTO.getUser_dob());
		user.setUser_password(userRequsetDTO.getUser_password());
		user.setUser_email(userRequsetDTO.getUser_email());
		user.setUser_role(userRequsetDTO.getUser_role());
		user.setUser_update_date(LocalDateTime.now());
		userRepo.save(user);
		return mapper.userToDto(user);
 	}else {
 		throw new Exception("User not found");
 	}
	}

	public UserResponseDTO updatePassword(String id, ChangePassword changePassword) throws Exception {
		User user = userRepo.findByUsername(id);
		if(user!=null) {
		user.setUser_password(passwordEncoder.encode(changePassword.getNew_password()));
		userRepo.save(user);
		return mapper.userToDto(user);
	}else {
		throw new Exception("User not found");
	}
	}

	public User getUserByUsername(String username) {
		return  userRepo.findByUsername(username);
		
	}
     @Transactional
	public String deleteUser(String username) {
		userRepo.deleteByUsername(username);
		return "user deleted successfully";
	}

	public User getUser(long id) throws UserNotFoundException {
		 
		return userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User not foud for id: "+id));
	}

}
