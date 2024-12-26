package com.example.redunm.cart;

import com.example.redunm.modellist.DataModel;
import com.example.redunm.modellist.DataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelCartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private DataModelRepository dataModelRepository;

    // 장바구니에 아이템 추가 (tag 기준)
    public Cart addToCartByUsernameAndTag(String username, String tag) {
        Cart cart = cartRepository.findByUsername(username).orElse(new Cart(username));

        DataModel dataModel = dataModelRepository.findByTag(tag)
                .orElseThrow(() -> new IllegalArgumentException("Model not found: " + tag));

        cart.addItem(dataModel);
        return cartRepository.save(cart);
    }

    // 장바구니에서 아이템 제거 (tag 기준)
    public Cart removeItemByUsernameAndTag(String username, String tag) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for username: " + username));

        cart.removeItemByTag(tag);
        return cartRepository.save(cart);
    }

    // 장바구니의 총합 금액 계산
    public double calculateTotalPriceByUsername(String username) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for username: " + username));

        return cart.calculateTotalPrice();
    }

    // 장바구니 조회
    public Cart getCartByUsername(String username) {
        return cartRepository.findByUsername(username)
                .orElse(new Cart(username));
    }

    // 장바구니 초기화
    public void clearCartByUsername(String username) {
        Cart cart = cartRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for username: " + username));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
