package com.Files.BookModel.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
	private long b_id;
	private String b_name;
	private String b_author;
	private float b_price;
	private long b_quantity;
	private String image;
}
