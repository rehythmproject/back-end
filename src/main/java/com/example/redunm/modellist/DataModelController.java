package com.example.redunm.modellist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.redunm.entity.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class DataModelController {

    @Autowired
    private DataModelRepository dataModelRepository;

    @GetMapping
    public ResponseEntity<?> getAllModels(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // 로그인이 안돼있으면 401 오류 뜨게하기
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        // 로그인 된 경우 mongoDB에서 model 조회
        List<DataModel> models = dataModelRepository.findAll();
        return ResponseEntity.ok(models);
    }
}
