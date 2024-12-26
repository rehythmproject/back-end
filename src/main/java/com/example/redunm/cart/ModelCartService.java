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

    // 1. username과 name으로 장바구니에 아이템 추가
    public Cart addToCartByUsernameAndModelName(String username, String name) {
        Cart cart = cartRepository.findByUserId(username).orElse(new Cart(username));

        DataModel dataModel = dataModelRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Model not found: " + name));

        cart.addItem(dataModel);
        return cartRepository.save(cart);
    }

    // 2. username과 name으로 장바구니에서 특정 아이템 제거
    public Cart removeItemByUsernameAndModelName(String username, String name) {
        Cart cart = cartRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for username: " + username));

        cart.removeItemByName(name);
        return cartRepository.save(cart);
    }

    // 3. username으로 장바구니의 총합 금액 계산
    public double calculateTotalPriceByUsername(String username) {
        Cart cart = cartRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for username: " + username));

        return cart.calculateTotalPrice();
    }

    // username기준으로 장바구니 조회
    public Cart getCartByUsername(String username) {
        return cartRepository.findByUserId(username)
                .orElse(new Cart(username));
    }

    //장바구니 초기화
    public void clearCartByUsername(String username) {
        Cart cart = cartRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for username: " + username));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
