package com.example.redunm.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class DataModelCartController {

    @Autowired
    private ModelCartService modelCartService;

    // + 버튼 클릭 시 모델 추가
    @PostMapping("/{username}/add/{tag}")
    public ResponseEntity<Cart> addToCart(@PathVariable String username, @PathVariable String tag) {
        return ResponseEntity.ok(modelCartService.addToCartByUsernameAndTag(username, tag));
    }

    // - 버튼 클릭 시 모델 제거
    @DeleteMapping("/{username}/remove/{tag}")
    public ResponseEntity<Cart> removeItem(@PathVariable String username, @PathVariable String tag) {
        return ResponseEntity.ok(modelCartService.removeItemByUsernameAndTag(username, tag));
    }

    // 장바구니 조회
    @GetMapping("/{username}")
    public ResponseEntity<Cart> getCart(@PathVariable String username) {
        return ResponseEntity.ok(modelCartService.getCartByUsername(username));
    }

    // 장바구니의 총 금액 계산
    @GetMapping("/{username}/total")
    public ResponseEntity<Double> calculateTotal(@PathVariable String username) {
        return ResponseEntity.ok(modelCartService.calculateTotalPriceByUsername(username));
    }
}
