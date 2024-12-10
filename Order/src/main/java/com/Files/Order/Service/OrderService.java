package com.Files.Order.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Files.Order.Address;
import com.Files.Order.Order;
import com.Files.Order.OrderItem;
import com.Files.Order.DTos.GetOrdersDto;
import com.Files.Order.DTos.Mapper;
import com.Files.Order.DTos.OrderPlaced;
import com.Files.Order.clients.BookClient;
import com.Files.Order.clients.CartClient;
import com.Files.Order.clients.UserClient;
import com.Files.Order.repos.OrderRepo;
import com.external.Book;
import com.external.Cart;
import com.external.User;

@Service
public class OrderService {

	@Autowired
	Mapper mapper;

	@Autowired
	UserClient userClient;

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	CartClient cartClient;

	@Autowired
	BookClient bookClient;


	public OrderPlaced placeOrder(User authenticatedUser, Address address,List<Cart> cartItems) {


		if (cartItems.isEmpty()) {
			throw new IllegalArgumentException("No items in the cart for user: ");
		}

		long userId = authenticatedUser.getUser_id();

		Order order = new Order();
		order.setUserId(userId);
		order.setCancel(false);
		order.setOrdDate(LocalDate.now());
		order.setAddress(address);


		float totalPrice = 0;
		long totalQuantity = 0;

		for (Cart item : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setUserId(userId);
			orderItem.setBookId(item.getBId());
			orderItem.setPrice(item.getTotalPrice());
			orderItem.setQuantity(item.getCartQuantity());

			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
			totalPrice += item.getTotalPrice();
			totalQuantity += item.getCartQuantity();
		}
		order.setTotalPrice(totalPrice);
		order.setTotalQuantity(totalQuantity);
		orderRepo.save(order);	    
		OrderPlaced orderToDto = mapper.orderPlaced(order, authenticatedUser);

		for(OrderItem item:order.getOrderItems()) {
			long units = item.getQuantity();
			Book book = bookClient.showbook(item.getBookId());
			System.out.println("Book issssssss"+book);
			if(book!=null) {
				long oldQuantity = book.getB_quantity();
				long newQuantity=oldQuantity-units;
 				bookClient.updateQuantity(book.getB_id(), newQuantity);
			}
			else {
				System.out.println("Book not found for Id :"+item.getBookId());
			}

		}
		return orderToDto;
	}



	public Boolean cancelOrder(long orderId) throws  Exception {

		Order order=orderRepo.findById(orderId).orElseThrow(()-> new Exception("Order not found"));
 
		if (order.isCancel()) {
			return false;  
		}


		order.setCancel(true);
		for (OrderItem item : order.getOrderItems()) {
		    long units = item.getQuantity();
		    Book book = bookClient.showbook(item.getBookId());
		    System.out.println("Old Quantity for Book ID " + book.getB_id() + ": " + book.getB_quantity());
		    long newQuantity = book.getB_quantity() + units;
		    System.out.println("New Quantity for Book ID " + book.getB_id() + ": " + newQuantity);
		    bookClient.updateQuantity(book.getB_id(), newQuantity);
		}
 
		orderRepo.save(order);
		return true;
	}


	public List<GetOrdersDto> getAllOrders(long user_id) {
		List<Order> orderItems = orderRepo.findAllByUserId(user_id);
		return orderItems.stream()
			.filter(order->!order.isCancel())
				.map(order ->mapper.convert(order))  
				.collect(Collectors.toList());
	}


}

