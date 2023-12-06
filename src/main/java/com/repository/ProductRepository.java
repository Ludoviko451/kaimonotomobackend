package com.repository;
import com.document.Product;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findProductsByIdTienda(String idTienda);
}