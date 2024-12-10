 package com.Files.Order.DTos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private long orderItemId;
    private long bookId;
    private float price;
    private long quantity;
}
