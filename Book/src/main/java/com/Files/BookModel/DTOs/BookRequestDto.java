package com.Files.BookModel.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
	private String b_name;
	private String b_author;
	private String b_description;
	private float b_price;
	private int b_quantity;
}
