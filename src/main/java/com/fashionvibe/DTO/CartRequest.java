package com.fashionvibe.DTO;

import lombok.Data;

@Data
public class CartRequest {

    private Long productId;
    private int quantity;
}
