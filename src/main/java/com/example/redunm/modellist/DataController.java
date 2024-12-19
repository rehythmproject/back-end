package com.example.redunm.modellist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/models")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public String getModelList(Model model) {
        List<DataModel> models = dataService.getAllModels();
        model.addAttribute("models", models);
        return "modelList";
    }
}
