package com.Files.CartModel.DTOs;

import java.util.List;

import com.Files.CartModel.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private String message;
    private List<Cart> cartItems;   

     
}