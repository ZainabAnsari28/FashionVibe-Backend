package com.fashionvibe.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionvibe.Entity.CartItem;
import com.fashionvibe.Entity.Product;
import com.fashionvibe.Entity.User;
import com.fashionvibe.Repository.CartItemRepository;
import com.fashionvibe.Repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public CartItem addToCart(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }

    public CartItem updateQuantity(User user, Long productId, int quantity) {
        List<CartItem> cartItems = getCartItems(user);
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                return cartItemRepository.save(item);
            }
        }
        throw new RuntimeException("Product not found in cart");
    }

}
