package com.example.redunm.modellist;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataModelRepository extends MongoRepository<DataModel, String> {
    Optional<DataModel> findByTag(String name);
}
