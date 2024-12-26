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

    //장바구니 아이템 추가
    public List<DataModel> addToCart(String userId, DataModel model) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(userId));

        cart.getItems().add(model);
        cartRepository.save(cart);

        return cart.getItems();
    }

    //특정 사용자 장바구니 목록 조회
    public List<DataModel> getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .map(Cart::getItems)
                .orElse(Collections.emptyList());
    }

    //장바구니 전체 비우기
    public void clearCart(String userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }

 //장바구니에서 특정모델 제거
    public List<DataModel> removeModel(String userId, String modelId) {
        return cartRepository.findByUserId(userId)
                .map(cart -> {
                    cart.getItems().removeIf(item -> item.getId().equals(modelId));
                    cartRepository.save(cart);
                    return cart.getItems();
                })
                .orElse(Collections.emptyList());
    }
}
