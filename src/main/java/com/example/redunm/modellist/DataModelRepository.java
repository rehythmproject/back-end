package com.example.redunm.modellist;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DataModelRepository extends MongoRepository<DataModel, String> {
    Optional<DataModel> findByName(String name);
}
