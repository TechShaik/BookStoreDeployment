package com.Files.UserEntity.DTOs;

import com.Files.UserEntity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
	
	private User user;
	
	private String username;
	private String password;
	public void setUsername(String username) {
		this.username = user.getUsername();
	}
	public void setPassword(String password) {
		this.password = user.getUser_password();
	}

	
}
