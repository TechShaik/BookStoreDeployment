package com.Files.Order.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.external.Cart;



@FeignClient(name ="cart-services")
public interface CartClient {
	@GetMapping("cart/showCartByUser/{userid}")
	public  List<Cart> showCartByUserId(@RequestHeader("Authorization")String authHeader,@PathVariable long userid) ;

	 @DeleteMapping("cart/removeUser/{userid}")
	   public ResponseEntity<String> removeByUserId(@RequestHeader("Authorization") String authHeader,@PathVariable long userid);
	  
}


