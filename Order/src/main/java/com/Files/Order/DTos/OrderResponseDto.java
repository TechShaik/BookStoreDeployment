package com.Files.Order.DTos;

import java.time.LocalDate;
import java.util.List;

import com.Files.Order.Address;
import com.external.User;
import com.Files.Order.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
	private long orderId;
	private LocalDate orderDate;
	private float price;
	private Address adress;
	private User user;
	private List<OrderItem> orders;


}
