package com.fashionvibe.DTO;

import lombok.Data;

@Data
public class PaymentRequest {

    private String method;
    private String upiId;
    private String cardNumber;
    private String expiry;
    private String cvv;
}
