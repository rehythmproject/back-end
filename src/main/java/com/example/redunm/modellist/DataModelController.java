package com.example.redunm.modellist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DataModelController {

    @Autowired
    private DataModelRepository DataModelRepository;

    @GetMapping("/models")
    public String getAllModels(Model model) {
        // MongoDB에서 모든 모델 데이터를 조회
        List<DataModel> models = DataModelRepository.findAll();
        model.addAttribute("models", models); // 데이터를 뷰로 전달
        return "modellist"; // modellist.html 렌더링
    }
}
