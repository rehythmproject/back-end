package com.example.redunm.modellist;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/modelcart")
public class ModelCartController {

    private final List<CartItem> cart = new ArrayList<>();

    @PostMapping("/add")
    public List<CartItem> addToCart(@RequestBody CartItem item) {
        for (CartItem cartItem : cart) {
            if (cartItem.getModelId().equals(item.getModelId())) {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                return cart;
            }
        }
        cart.add(item);
        return cart;
    }

    @PostMapping("/remove")
    public List<CartItem> removeFromCart(@RequestBody String modelId) {
        cart.removeIf(item -> item.getModelId().equals(modelId));
        return cart;
    }

    @GetMapping
    public List<CartItem> getCart() {
        return cart;
    }

    @PostMapping("/clear")
    public void clearCart() {
        cart.clear();
    }
}