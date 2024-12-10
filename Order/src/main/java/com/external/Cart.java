package com.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	private long cartId;

	private long cartQuantity;

	private float totalPrice;

	private long userId;

	private long bId;
}