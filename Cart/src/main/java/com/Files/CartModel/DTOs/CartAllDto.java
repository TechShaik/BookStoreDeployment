package com.Files.CartModel.DTOs;


import com.Files.CartModel.External.Book;
import com.Files.CartModel.External.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAllDto {
	private long cart_Id;
	private long quantity;
	private float total_price;
	private Book book;
	private User user;
}
