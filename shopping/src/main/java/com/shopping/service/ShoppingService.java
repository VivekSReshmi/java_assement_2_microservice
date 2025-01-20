package com.shopping.service;

import com.shopping.entity.Shopping;
import com.shopping.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ShoppingRepository shoppingRepository;

    public List<Shopping> getCartItems(Long customerId) {
        return shoppingRepository.findByCustomerId(customerId);
    }

    public Optional<Shopping> getCartItemById(Long id) {
        return shoppingRepository.findById(id);
    }

    public Shopping addItemToCart(Shopping shoppingItem) {
        // Add any business logic or validation here
        return  null;
    }

    public Shopping updateCartItem(Long id, Shopping updatedItem) {
        if (!shoppingRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart item not found");
        }
        updatedItem.setId(id);
        return shoppingRepository.save(updatedItem);
    }

    public void removeCartItem(Long id) {
        if (!shoppingRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart item not found");
        }
        shoppingRepository.deleteById(id);
    }
}
