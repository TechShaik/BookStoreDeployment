package com.Files.CartModel.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.Files.CartModel.External.Book;

@FeignClient(name="book-services")
public interface BookClient {
	@GetMapping("/books/{id}")
	Book getBook(@PathVariable long id);

	@PutMapping("/books/minusBecauseInCartAdded/{id}")
	Book minusBook(@RequestHeader("Authorization") String authHeader,@PathVariable("id") long id,@RequestParam("changeQuantity") long changeQuantity);
}

