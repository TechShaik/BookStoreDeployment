package com.Files.Order.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.external.User;


@FeignClient(name="user-services")
public interface UserClient {
	@GetMapping("users/{id}")
	User getUser(@RequestHeader("Authorization") String authHeader,@PathVariable long id);


	@GetMapping("/users/getUser")
	public User getUserByToken(@RequestHeader("Authorization") String authHeader);

	@GetMapping("users/get/{id}")
	public User getUser(@PathVariable long id);

}