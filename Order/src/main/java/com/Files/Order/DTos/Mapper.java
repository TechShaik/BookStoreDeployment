package com.Files.Order.DTos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Files.Order.Order;
import com.Files.Order.OrderItem;
import com.Files.Order.clients.UserClient;
import com.external.User;

@Component
public class Mapper {

	@Autowired
	private UserClient userClient;


	public GetOrdersDto convert(Order order) {
		GetOrdersDto dto = new GetOrdersDto();

		long userId = order.getUserId();
		User user = userClient.getUser(userId);

		dto.setOrderDate(order.getOrdDate());
		dto.setName(user.getF_name() + " " + user.getL_name());
		dto.setEmail(user.getUser_email());
		dto.setAddress(order.getAddress());
		dto.setTotalPrice(order.getTotalPrice());
		dto.setQuantity(order.getTotalQuantity());

		List<OrderItemDto> orderItemDtos = order.getOrderItems()
				.stream()
				.map(this::convertOrderItemToDto)
				.collect(Collectors.toList());
		dto.setOrderItems(orderItemDtos);

		return dto;
	}

	private OrderItemDto convertOrderItemToDto(OrderItem orderItem) {
		OrderItemDto dto = new OrderItemDto();
		dto.setOrderItemId(orderItem.getOrderItemId());
		dto.setBookId(orderItem.getBookId());
		dto.setPrice(orderItem.getPrice());
		dto.setQuantity(orderItem.getQuantity());
		return dto;
	}

	public OrderPlaced orderPlaced(Order order, User user) {
		OrderPlaced orderPlaced = new OrderPlaced();

		orderPlaced.setName(user.getF_name() + " " + user.getL_name());
		orderPlaced.setOrderId(order.getOrdId());
		orderPlaced.setOrderDate(order.getOrdDate());
		orderPlaced.setPrice(order.getTotalPrice());
		orderPlaced.setUser_email(user.getUser_email());
		orderPlaced.setAdress(order.getAddress());

		List<OrderItemDto> orderItemDtos = order.getOrderItems()
				.stream()
				.map(this::convertOrderItemToDto)
				.collect(Collectors.toList());
		orderPlaced.setOrders(orderItemDtos);

		return orderPlaced;
	}
}
