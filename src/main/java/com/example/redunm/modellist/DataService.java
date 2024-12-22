package com.example.redunm.modellist;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DataService {

    private final DataModelRepository dataRepository;

    public DataService(DataModelRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<DataModel> getAllModels() {
        return dataRepository.findAll();
    }
}