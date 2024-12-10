package Mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.Files.CartModel.Cart;
import com.Files.CartModel.Clients.BookClient;
import com.Files.CartModel.Clients.UserClient;
import com.Files.CartModel.DTOs.CartAllDto;
import com.Files.CartModel.External.Book;
import com.Files.CartModel.External.User;

public class Mapper {

	@Autowired
	UserClient userClient;

	@Autowired
	BookClient bookClient;

	public CartAllDto mapToDto(Cart cart) {
		Book book=bookClient.getBook(cart.getBId());
		User user=userClient.getUser(cart.getUserId());
		CartAllDto allDto=convertWithAll(cart,user,book);
		allDto.setUser(user);
		allDto.setBook(book);
		return allDto;		
	}



	public static CartAllDto convertWithAll(Cart cart,User user,Book book) {
		CartAllDto cartAllDto=new CartAllDto();
		cartAllDto.setCart_Id(cart.getCartId());
		cartAllDto.setQuantity(cart.getCartQuantity());
		cartAllDto.setTotal_price(cart.getTotalPrice());
		cartAllDto.setUser(user);
		cartAllDto.setBook(book);
		return cartAllDto;
	}
}
