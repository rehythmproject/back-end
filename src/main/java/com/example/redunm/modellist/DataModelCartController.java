package com.example.redunm.modellist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data-models")
public class DataModelCartController {

    @Autowired
    private DataModelRepository dataModelRepository;

    @Autowired
    private ModelCartService cartService;

    @GetMapping
    public List<DataModel> getAll() {
        return dataModelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataModel> getById(@PathVariable String id) {
        return dataModelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<DataModel> create(@RequestBody DataModel model) {
        DataModel savedModel = dataModelRepository.save(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataModel> update(@PathVariable String id, @RequestBody DataModel model) {
        if (!dataModelRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        model.setId(id);
        DataModel updatedModel = dataModelRepository.save(model);
        return ResponseEntity.ok(updatedModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!dataModelRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        dataModelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 관련 API
    @PostMapping("/cart/{userId}/{modelId}")
    public ResponseEntity<List<DataModel>> addToCart(@PathVariable String userId,
                                                     @PathVariable String modelId) {
        DataModel foundModel = dataModelRepository.findById(modelId).orElse(null);
        if (foundModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }

        List<DataModel> updatedCart = cartService.addToCart(userId, foundModel);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<DataModel>> getCart(@PathVariable String userId) {
        List<DataModel> cartItems = cartService.getCart(userId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/cart/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart/{userId}/{modelId}")
    public ResponseEntity<List<DataModel>> removeModelFromCart(@PathVariable String userId,
                                                               @PathVariable String modelId) {
        List<DataModel> updatedCart = cartService.removeModel(userId, modelId);
        return ResponseEntity.ok(updatedCart);
    }
}
