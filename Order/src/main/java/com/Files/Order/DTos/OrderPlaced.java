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
public class OrderPlaced {
	private String name;
	private String user_email;

	private long orderId;
	private LocalDate orderDate;
	private float price;
	private Address adress;
	private List<OrderItemDto> orders;



}
