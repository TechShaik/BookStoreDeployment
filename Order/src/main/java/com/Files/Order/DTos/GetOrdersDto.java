package com.Files.Order.DTos;

import java.time.LocalDate;
import java.util.List;


import com.Files.Order.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersDto {

	private LocalDate orderDate;
	String name;
	String email;
	float totalPrice;
	long quantity;
	private Address address;
	private List<OrderItemDto> orderItems;


}
