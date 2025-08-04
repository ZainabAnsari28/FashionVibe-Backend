package com.fashionvibe.Service;

import org.springframework.stereotype.Service;

import com.fashionvibe.DTO.PaymentRequest;

@Service
public class PaymentService {

    public boolean processPayment(PaymentRequest request) {
        String method = request.getMethod() != null ? request.getMethod().trim().toUpperCase() : "";
        System.out.println("Received method: " + method);

        switch (method) {
            case "UPI":

                String upi = request.getUpiId() != null ? request.getUpiId().trim().toLowerCase() : "";
                System.out.println("Received UPI ID: " + upi);
                System.out.println("Is UPI valid? " + (!upi.isEmpty() && upi.endsWith("@upi")));
                return !upi.isEmpty() && upi.endsWith("@upi");

            case "CREDIT_CARD":
            case "DEBIT_CARD":
                String card = request.getCardNumber() != null ? request.getCardNumber().trim() : "";
                String cvv = request.getCvv() != null ? request.getCvv().trim() : "";
                return card.length() == 16 && cvv.length() == 3;

            default:
                return false;
        }
    }
}
