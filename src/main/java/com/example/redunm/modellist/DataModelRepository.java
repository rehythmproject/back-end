package com.example.redunm.modellist;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataModelRepository extends MongoRepository<DataModel, String> {
}
