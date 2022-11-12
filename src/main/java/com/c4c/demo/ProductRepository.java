package com.c4c.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "productdata",path="productdata")
public interface ProductRepository extends MongoRepository<ProductSave,String> {

}
