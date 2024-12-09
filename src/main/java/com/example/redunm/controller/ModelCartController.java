package com.example.redunm.controller;

import com.example.redunm.entity.CartItem;
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

    // 장바구니 항목 제거 하는 기능
    @PostMapping("/remove")
    public List<CartItem> removeFromCart(@RequestBody String modelId) {
        cart.removeIf(item -> item.getModelId().equals(modelId));
        return cart;
    }

    // 장바구니 항목 조회 하는 기능
    @GetMapping
    public List<CartItem> getCart() {
        return cart;
    }

    // 장바구니 초기화 하는 기능
    @PostMapping("/clear")
    public void clearCart() {
        cart.clear();
    }
}
