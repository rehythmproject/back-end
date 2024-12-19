package com.example.redunm.modellist;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepository extends MongoRepository<DataModel, String> {
}
