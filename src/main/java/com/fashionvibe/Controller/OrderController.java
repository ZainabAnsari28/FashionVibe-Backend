package com.fashionvibe.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionvibe.DTO.PaymentRequest;
import com.fashionvibe.Entity.CartItem;
import com.fashionvibe.Entity.Order;
import com.fashionvibe.Entity.User;
import com.fashionvibe.Repository.UserRepository;
import com.fashionvibe.Service.CartService;
import com.fashionvibe.Service.OrderService;
import com.fashionvibe.Service.PaymentService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PaymentRequest request) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        boolean paymentSuccess = paymentService.processPayment(request);

        if (!paymentSuccess) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
        }

        List<CartItem> cartItems = cartService.getCartItems(user);
        Order order = orderService.placeOrder(user, cartItems);

        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        System.out.println("Inside getUserOrders");
        System.out.println("UserDetails: " + userDetails);

        return ResponseEntity.ok(orderService.getUserOrders(user));
    }
}
