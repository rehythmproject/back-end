package com.example.redunm.modellist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/data-models")
public class DataModelCartController {

    @Autowired
    private DataModelRepository dataModelRepository;

    /**
     * [임시] 사용자별 장바구니 목록을 저장할 Map
     *   - key: 사용자 ID (문자열)
     *   - value: 해당 사용자가 장바구니에 담은 DataModel 리스트
     */
    private Map<String, List<DataModel>> userCartMap = new HashMap<>();

    //모델 생성,삭제,추가,수정
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

    //장바구니 기능
    @PostMapping("/cart/{userId}/{modelId}")
    public List<DataModel> addToCart(@PathVariable String userId,
                                     @PathVariable String modelId) {
        DataModel selectedModel = dataModelRepository.findById(modelId).orElse(null);
        if (selectedModel == null) {
            return Collections.emptyList();
        }

        List<DataModel> cartList = userCartMap.getOrDefault(userId, new ArrayList<>());

        cartList.add(selectedModel);

        userCartMap.put(userId, cartList);

        return cartList;
    }

   //장바구니 목록 조회
    @GetMapping("/cart/{userId}")
    public List<DataModel> getCart(@PathVariable String userId) {
        return userCartMap.getOrDefault(userId, Collections.emptyList());
    }
}
