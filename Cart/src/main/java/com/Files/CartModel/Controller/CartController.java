package com.Files.CartModel.Controller;

import java.util.Collections;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Files.CartModel.Cart;
import com.Files.CartModel.Clients.BookClient;
import com.Files.CartModel.Clients.UserClient;
import com.Files.CartModel.DTOs.CartResponseDto;
import com.Files.CartModel.External.Book;
import com.Files.CartModel.External.User;
import com.Files.CartModel.Service.CartService;

import ExceptionHandler.CartNotFoundException;


@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	UserClient userCient;

	@Autowired
	BookClient bookClient;

	@Autowired
	CartService cartService;

	private static final String CART_SERVICE="cartService";

	@GetMapping
	public String Home() {
		return "Hey user add items first";
	}

	@PostMapping("/add/{bId}")
	public ResponseEntity<?> addToCart(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable long bId,
			@RequestParam long units) {


		User authenticatedUser = userCient.getUserByToken(authHeader);
		if (authenticatedUser == null) {
			return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
		}
		Book book = bookClient.getBook(bId);
		if (units <= 0  || book.getB_quantity()<units) {
			return new ResponseEntity<>("Invalid quantity specified", HttpStatus.BAD_REQUEST);
		}else {

			long user_id = authenticatedUser.getUser_id();
			Cart cart = cartService.addToCart(user_id, bId, units);

			if (cart != null) {
				return new ResponseEntity<>(cart, HttpStatus.CREATED);   
			} else {
				return new ResponseEntity<>("Error adding to cart", HttpStatus.INTERNAL_SERVER_ERROR);  
			}
		}
	}





	@GetMapping("/showCartByUser/{userid}")
	public  List<Cart> showCartByUserId( @RequestHeader("Authorization") String authHeader,@PathVariable long userid) throws Exception {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new Exception( "Authorization header is missing or invalid");
		}

		User authenticatedUser = userCient.getUserByToken(authHeader);
		if (authenticatedUser == null) {
			throw new Exception("User not authenticated");
		}

		return cartService.showCartByUserId(userid);

	}

	public   ResponseEntity<CartResponseDto> getUserCartId(Exception e){
		CartResponseDto response = new CartResponseDto(
				"Oops! Cart services are currently unavailable. Please try again later.",
				Collections.emptyList() 
				);
		return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}


	@GetMapping("/showCartByUser")
	public  List<Cart> showCartByUserId( @RequestHeader("Authorization") String authHeader) throws Exception {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new Exception( "Authorization header is missing or invalid");
		}

		User authenticatedUser = userCient.getUserByToken(authHeader);
		if (authenticatedUser == null) {
			throw new Exception("User not authenticated");
		}
		long user_id = authenticatedUser.getUser_id();

		return cartService.showCartByUserId(user_id);
	}



	@DeleteMapping("/removeByCartId/{cartId}")
	public ResponseEntity<String> removeByCartId(@RequestHeader("Authorization") String authHeader,@PathVariable long cartId){
		return cartService.removeByCartId(cartId);
	}



	@DeleteMapping("/removeUser")
	public ResponseEntity<String> removeByToken(@RequestHeader("Authorization") String authHeader) {
		User authenticatedUser = userCient.getUserByToken(authHeader);
		System.out.println(authHeader);
		long user_id = authenticatedUser.getUser_id(); 	       


		return cartService.removeByToken(user_id);
	}

	@DeleteMapping("/removeUser/{userid}")
	public ResponseEntity<String> removeByUserId(@RequestHeader("Authorization") String authHeader,@PathVariable long userid) {


		return cartService.removeByUserId(userid);
	}

	@PutMapping("/updateQuantity/{cartId}")
	public ResponseEntity<?> updateQuantity(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable long cartId,
			@RequestParam long quantity) throws CartNotFoundException {

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.BAD_REQUEST);
		}

		User authenticatedUser = userCient.getUserByToken(authHeader);
		if (authenticatedUser == null) {
			return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
		}

		if (quantity <= 0) {
			return new ResponseEntity<>("Invalid quantity specified", HttpStatus.BAD_REQUEST);
		}

		long user_id = authenticatedUser.getUser_id();
		Cart cart = cartService.updateQuantity( cartId, quantity,user_id);

		if (cart != null) {
			return new ResponseEntity<>(cart, HttpStatus.CREATED);   
		} else {
			return new ResponseEntity<>("Error adding to cart", HttpStatus.INTERNAL_SERVER_ERROR);  
		}
	}

	@GetMapping("/getallcartitemsforuser")
	public ResponseEntity<?> getallcartitemsforuser(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.BAD_REQUEST);
		}

		User authenticatedUser = userCient.getUserByToken(authHeader);
		if (authenticatedUser == null) {
			return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
		}

		long user_id = authenticatedUser.getUser_id();

		return new ResponseEntity<>(cartService.getallcartitemsforuser(user_id), HttpStatus.OK);
	}




}
