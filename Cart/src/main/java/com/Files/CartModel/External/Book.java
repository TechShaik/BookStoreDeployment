package com.Files.CartModel.External;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	private long b_id;
	private String b_name;
	private String b_author;
	private String b_description;
	private float b_price;
	private int b_quantity;

}
