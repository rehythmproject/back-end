package com.example.redunm.modellist;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DataService {

    private final DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<DataModel> getAllModels() {
        return dataRepository.findAll();
    }
}