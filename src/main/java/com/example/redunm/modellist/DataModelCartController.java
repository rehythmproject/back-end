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
    private ModelCartService modelcartService;

    @PostMapping("/{username}/add/{name}")
    public ResponseEntity<Cart> addToCart(@PathVariable String userId, @PathVariable String modelId) {
        return ResponseEntity.ok(modelcartService.addToCartByUsernameAndModelName(userId, modelId));
    }

    @DeleteMapping("/{username}/remove/{name}")
    public ResponseEntity<Cart> removeItem(@PathVariable String userId, @PathVariable String modelId) {
        return ResponseEntity.ok(modelcartService.removeItemByUsernameAndModelName(userId, modelId));
    }

    //합계 총금액
    @GetMapping("/{username}/total")
    public ResponseEntity<Double> calculateTotal(@PathVariable String userId) {
        return ResponseEntity.ok(modelcartService.calculateTotalPriceByUsername(userId));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(modelcartService.getCartByUsername(userId));
    }

}

