//package com.example.redunm;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import com.example.redunm.entity.Cart;
//import com.example.redunm.entity.CartItem;
//import com.example.redunm.modellist.DataModel;
//import com.example.redunm.modellist.CartRepository;
//import com.example.redunm.modellist.DataModelRepository;
//
//import java.util.Arrays;
//
//@Component
//public class CartDataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private DataModelRepository dataModelRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        createMockData();
//    }
//
//    private void createMockData() {
//        // 가상 DataModel 생성 및 저장
//        DataModel model1 = new DataModel("Model1", "Tag1", 100);
//        DataModel model2 = new DataModel("Model2", "Tag2", 200);
//        DataModel model3 = new DataModel("Model3", "Tag3", 300);
//
//        dataModelRepository.saveAll(Arrays.asList(model1, model2, model3));
//
//        // 가상 Cart 생성 및 저장
//        Cart cart1 = new Cart("user1");
//        cart1.addItem(model1);
//        cart1.addItem(model2);
//
//        Cart cart2 = new Cart("user2");
//        cart2.addItem(model2);
//        cart2.addItem(model3);
//
//        cartRepository.saveAll(Arrays.asList(cart1, cart2));
//
//        System.out.println("가상 데이터가 MongoDB에 저장되었습니다.");
//    }
//}
