package com.example.redunm.modellist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data-models")
public class DataModelCartController {

    @Autowired
    private DataModelRepository dataModelRepository;

    @GetMapping
    public List<DataModel> getAll() {
        return dataModelRepository.findAll();
    }

    @GetMapping("/{id}")
    public DataModel getById(@PathVariable String id) {
        return dataModelRepository.findById(id)
                .orElse(null);
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
}
