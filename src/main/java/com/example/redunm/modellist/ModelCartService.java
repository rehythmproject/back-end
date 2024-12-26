package com.example.redunm.modellist;

import com.example.redunm.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ModelCartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private DataModelRepository dataModelRepository;

    // 장바구니 아이템 추가
    public Cart addToCart(String userId, String modelId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId));

        DataModel dataModel = dataModelRepository.findById(modelId)
                .orElseThrow(() -> new IllegalArgumentException("Model not found: " + modelId));

        cart.addItem(dataModel);
        return cartRepository.save(cart);
    }

    // 장바구니에서 특정 아이템 제거
    public Cart removeItem(String userId, String modelId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        cart.removeItem(modelId);
        return cartRepository.save(cart);
    }

    // 장바구니의 총합 금액 계산
    public double calculateTotalPrice(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        return cart.calculateTotalPrice();
    }

    // 특정 사용자 장바구니 조회
    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElse(new Cart(userId));
    }
}
