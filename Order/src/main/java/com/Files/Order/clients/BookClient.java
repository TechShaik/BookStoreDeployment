package com.Files.Order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.external.Book;


@FeignClient(name="book-services")
public interface BookClient {



	@GetMapping("/books/{id}")
	public Book showbook(@PathVariable long id);


	@PutMapping("books/update/{id}")
	public  Book updateQuantity(@PathVariable long id,@RequestParam long bookQuantityChange) ;








}

