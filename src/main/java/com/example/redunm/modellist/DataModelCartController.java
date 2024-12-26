package com.example.redunm.modellist;

import com.example.redunm.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class DataModelCartController {

    @Autowired
    private ModelCartService cartService;

    @PostMapping("/{userId}/{modelId}")
    public ResponseEntity<Cart> addToCart(@PathVariable String userId, @PathVariable String modelId) {
        return ResponseEntity.ok(cartService.addToCart(userId, modelId));
    }

    @DeleteMapping("/{userId}/{modelId}")
    public ResponseEntity<Cart> removeItem(@PathVariable String userId, @PathVariable String modelId) {
        return ResponseEntity.ok(cartService.removeItem(userId, modelId));
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<Double> calculateTotal(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.calculateTotalPrice(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}
