package com.Files.UserEntity.CustomClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Files.UserEntity.User;
import com.Files.UserEntity.Repos.UserRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Fetching user by username: " + username);

		User user = repo.findByUsername(username);

		if (user == null) {
			System.out.println("User not found in database");
			throw new UsernameNotFoundException("User not found: " + username);
		}

		System.out.println("User email: " + user.getUser_email());
		System.out.println("User found: " + user);

		return new CustomUserDetails(user); 
	}
}
