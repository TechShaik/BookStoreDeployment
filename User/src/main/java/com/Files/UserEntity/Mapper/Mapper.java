package com.Files.UserEntity.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.Files.UserEntity.User;
import com.Files.UserEntity.DTOs.LoginRequestDto;
 import com.Files.UserEntity.DTOs.UserRequsetDTO;
import com.Files.UserEntity.DTOs.UserResponseDTO;

@Component
public class Mapper {

	public UserResponseDTO userToDto(User user) {
		UserResponseDTO userResponseDTO =new UserResponseDTO();
 		userResponseDTO.setUsername(user.getUsername()); 
 		userResponseDTO.setName(user.getF_name()+" "+user.getL_name());
 		userResponseDTO.setUser_email(user.getUser_email()); 
 	return userResponseDTO;
	}
	
	
	public User dtoTOUser(UserRequsetDTO userRequsetDTO) {
		User user =new User();
		user.setUsername(userRequsetDTO.getUsername());
		user.setF_name(userRequsetDTO.getF_name());
		user.setL_name(userRequsetDTO.getL_name());
 		user.setUser_dob(userRequsetDTO.getUser_dob());
 		user.setUser_password(userRequsetDTO.getUser_password());
		user.setUser_email(userRequsetDTO.getUser_email());
		user.setUser_role(userRequsetDTO.getUser_role());
		user.setUser_update_date(LocalDateTime.now());
		user.setUser_reg_date(LocalDate.now());
 		return user;
	}
	
	
	 
}
