package com.example.redunm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.redunm.entity.Cart;
import com.example.redunm.entity.CartItem;
import com.example.redunm.modellist.DataModel;
import com.example.redunm.modellist.CartRepository;
import com.example.redunm.modellist.DataModelRepository;

import java.util.Collections;

@Component
public class CartDataInitializer implements CommandLineRunner {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private DataModelRepository dataModelRepository;

    @Override
    public void run(String... args) throws Exception {
        createMockData();
    }

    private void createMockData() {
        // 1. 가상 DataModel 생성 및 저장
        DataModel model = new DataModel();
        model.setName("della");
        model.setTag("meeting");
        model.setPrice(150);
        dataModelRepository.save(model);

        // 2. 가상 Cart 생성 및 저장
        Cart cart = new Cart("youjininny");
        cart.addItem(model);
        cartRepository.save(cart);

        System.out.println("가상 데이터가 성공적으로 저장되었습니다.");
        System.out.println("Cart: " + cart);
        System.out.println("DataModel: " + model);
    }
}
