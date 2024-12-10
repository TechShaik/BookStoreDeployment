package com.Files.Order.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Files.Order.Address; 
import com.Files.Order.DTos.OrderPlaced;
import com.Files.Order.JMS.EmailSenderServices;
import com.Files.Order.Service.OrderService;
import com.Files.Order.clients.BookClient;
import com.Files.Order.clients.CartClient;
import com.Files.Order.clients.UserClient;
import com.external.Cart;
import com.external.User;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	UserClient userClient;

	@Autowired
	BookClient bookClient;

	@Autowired
	private CartClient cartClient;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	EmailSenderServices emailSenderServices;

	@GetMapping
	public String Home() {
		return "Hey user, you can order now...!!!";
	}

	@PostMapping("/place")
	public OrderPlaced placeOrder(@RequestHeader("Authorization") String authHeader, @RequestBody Address address) throws Exception {
		System.out.println(authHeader);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new Exception("Missing or invalid Authorization header");
		}

		User authenticatedUser = userClient.getUserByToken(authHeader);  

		System.out.println("User Object: "+authenticatedUser);
		if (authenticatedUser == null) {
			throw new Exception("User not authenticated or token is invalid");
		}

		long userId = authenticatedUser.getUser_id();
		List<Cart> cartItems = cartClient.showCartByUserId(authHeader,userId);


		OrderPlaced placeOrder = orderService.placeOrder(authenticatedUser, address,cartItems);
		String body="Dear user your order placed successfully";

        emailSenderServices.sendMail(authenticatedUser.getUser_email(), "Order Confirmation", body, authenticatedUser);
		cartClient.removeByUserId(authHeader, userId);

		return placeOrder;
	}




	@GetMapping(value = "/getAllOrdersOfUser")
	public ResponseEntity<?> getAllOrdersOfUser(@RequestHeader("Authorization") String authHeader){
		User user=userClient.getUserByToken(authHeader);
		long userId = user.getUser_id();

		return new ResponseEntity<>(orderService.getAllOrders(userId),HttpStatus.OK);
	}

	@PutMapping("/cancel/{orderId}")
	public boolean cancelOrder(@RequestHeader("Authorization") String authHeader,@PathVariable long orderId) throws Exception {
		User authenticatedUser = userClient.getUserByToken(authHeader);
		if (authenticatedUser == null) {
			throw new Exception("User not authenticated or token is invalid");
		} else {
			String body="Dear user your order cancelled successfully";
			emailSenderServices.sendMail(authenticatedUser.getUser_email(), "Order Cancellation Confirmation", body, authenticatedUser);
			return orderService.cancelOrder(orderId);



		}
	}
}
