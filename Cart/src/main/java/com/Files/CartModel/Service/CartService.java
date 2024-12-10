package com.Files.CartModel.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Files.CartModel.Cart;
import com.Files.CartModel.Clients.BookClient;
import com.Files.CartModel.External.Book;
import com.Files.CartModel.Repos.CartRepo;

import ExceptionHandler.CartNotFoundException;
import jakarta.transaction.Transactional;
@Service
public class CartService {

	@Autowired
	private BookClient bookClient;

	@Autowired
	private CartRepo cartRepo;
 
	public Cart addToCart(long userId, long bId, long units) {
		Book book = bookClient.getBook( bId);

		Cart existingCart = cartRepo.findByUserIdAndBId(bId, userId);
		System.out.println(existingCart);

		Cart cart;
		if (existingCart != null) {
			long newCartQuantity = existingCart.getCartQuantity() + units;   
			existingCart.setCartQuantity(newCartQuantity);
			existingCart.setTotalPrice(book.getB_price() * newCartQuantity);   

			return cartRepo.save(existingCart);
		} else {
			cart = new Cart();
			cart.setBId(bId);   
			cart.setUserId(userId);  
			cart.setCartQuantity(units);  
			cart.setTotalPrice(book.getB_price() * units);  
			return cartRepo.save(cart);  
		}
	}
	
	public List<String> companyBreakerFallback(){
		List<String>list=new ArrayList<String>();
		list.add("Dummy");
		return list;
	}



	public ResponseEntity<String> removeByCartId(long cartId) {		 
		cartRepo.deleteById(cartId);
		return new ResponseEntity<String>("Items removed from cart",HttpStatus.OK);
	}



	@Transactional
	public ResponseEntity<String> removeByUserId(long user_id) {
		cartRepo.deleteAllByUserId(user_id);
		return new ResponseEntity<>("User's cart removed", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> removeByToken(long user_id) {
		cartRepo.deleteAllByUserId(user_id);
		return new ResponseEntity<>("User's cart removed", HttpStatus.OK);
	}





	public List<Cart> showCartByUserId(long userId) {		 
		return  cartRepo.findByUserId(userId);

	}


	public Cart updateQuantity(long cartId, long quantity, long user_id) throws CartNotFoundException {
	    // Fetch the cart by ID
	    Optional<Cart> optionalCart = cartRepo.findById(cartId);
	    
	    // Check if the cart exists
	    if (optionalCart.isEmpty()) {
	        throw new CartNotFoundException("No cart found for id: "+cartId);
	    }

	    Cart byId = optionalCart.get();
 
 	    byId.setCartQuantity(quantity);
	    byId.setTotalPrice(quantity * bookClient.getBook(byId.getBId()).getB_price());

	    // Save and return the updated cart
	    return cartRepo.save(byId);
	}




	public List<Cart> getallcartitemsforuser(long user_id) {
		return  cartRepo.findByUserId(user_id);
	}

	public List<Cart> showCartByUser(long user_id) {
		return  cartRepo.findByUserId(user_id);
	}







}






