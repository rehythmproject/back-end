package com.example.redunm.modellist;

import com.example.redunm.modellist.ModelCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data-models")
public class DataModelCartController {

    @Autowired
    private DataModelRepository dataModelRepository;

    @Autowired
    private ModelCartService cartService;  // 새로 만든 CartService

    // 1) DataModel 기본 CRUD
    @GetMapping
    public List<DataModel> getAll() {
        return dataModelRepository.findAll();
    }

    @GetMapping("/{id}")
    public DataModel getById(@PathVariable String id) {
        return dataModelRepository.findById(id).orElse(null);
    }

    @PostMapping
    public DataModel create(@RequestBody DataModel model) {
        return dataModelRepository.save(model);
    }

    @PutMapping("/{id}")
    public DataModel update(@PathVariable String id, @RequestBody DataModel model) {
        model.setId(id);
        return dataModelRepository.save(model);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        dataModelRepository.deleteById(id);
    }

    // 2) 장바구니 관련 API
    @PostMapping("/cart/{userId}/{modelId}")
    public List<DataModel> addToCart(@PathVariable String userId,
                                     @PathVariable String modelId) {
        // DB에서 modelId로 DataModel 조회
        DataModel foundModel = dataModelRepository.findById(modelId).orElse(null);
        if (foundModel == null) {
            // 없는 모델이면 빈 목록 반환
            return List.of();
        }

        // CartService를 통해 장바구니에 추가
        return cartService.addToCart(userId, foundModel);
    }

    // (B) 특정 사용자의 장바구니 조회
    @GetMapping("/cart/{userId}")
    public List<DataModel> getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    // (C) 장바구니 전체 비우기 (옵션)
    @DeleteMapping("/cart/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }

    // (D) 장바구니에서 특정 모델 제거 (옵션)
    @DeleteMapping("/cart/{userId}/{modelId}")
    public List<DataModel> removeModelFromCart(@PathVariable String userId,
                                               @PathVariable String modelId) {
        return cartService.removeModel(userId, modelId);
    }
}
