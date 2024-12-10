package com.Files.CartModel.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.Files.CartModel.External.User;

@FeignClient(name="user-services")
public interface UserClient {
	@GetMapping("users/{id}")
	User getUser(@PathVariable long id);

	@GetMapping("users/getUser")
	public User getUserByToken(@RequestHeader("Authorization") String authHeader);
}
