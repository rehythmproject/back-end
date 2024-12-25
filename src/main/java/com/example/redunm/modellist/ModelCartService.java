package com.example.redunm.modellist;

import com.example.redunm.modellist.DataModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 사용자별 장바구니(Cart)를
 * 서버 메모리 내부의 Map에 저장해두는 임시 서비스
 */
@Service
public class ModelCartService {

    /**
     * 사용자별 장바구니 목록을 저장할 Map
     * key: 사용자 ID (문자열)
     * value: 해당 사용자가 장바구니에 담은 DataModel 리스트
     *
     * - ConcurrentHashMap: 멀티쓰레드 환경에서 안전성 보장(간단 예시)
     * - 실무에서는 Redis나 DB에 저장하는 방식을 권장
     */
    private final Map<String, List<DataModel>> userCartMap = new ConcurrentHashMap<>();

    /**
     * 특정 사용자(userId)의 장바구니 목록 조회
     */
    public List<DataModel> getCart(String userId) {
        return userCartMap.getOrDefault(userId, Collections.emptyList());
    }

    /**
     * 장바구니에 모델 추가
     * @return 추가 후의 장바구니 전체 목록
     */
    public List<DataModel> addToCart(String userId, DataModel model) {
        // userId가 가진 장바구니 목록 가져오기 (없으면 새 리스트)
        List<DataModel> cartList = userCartMap.getOrDefault(userId, new ArrayList<>());
        cartList.add(model);

        // 갱신된 목록을 다시 put
        userCartMap.put(userId, cartList);

        return cartList;
    }

    /**
     * 장바구니 전체 삭제 (옵션)
     */
    public void clearCart(String userId) {
        userCartMap.remove(userId);
    }

    /**
     * 장바구니에서 특정 모델 삭제 (옵션)
     */
    public List<DataModel> removeModel(String userId, String modelId) {
        List<DataModel> cartList = userCartMap.get(userId);
        if (cartList == null) {
            return Collections.emptyList();
        }

        cartList.removeIf(m -> m.getId().equals(modelId));
        userCartMap.put(userId, cartList);
        return cartList;
    }
}
