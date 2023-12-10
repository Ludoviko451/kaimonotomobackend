package com.repository;
import com.document.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findProductsByIdTienda(String idTienda);

    // List<Product> findByNombreContainingIgnoreCase(String searchText);

    // List<Product> findByEtiquetasIn(List<String> tags);

    // List<Product> findByNombreContainingIgnoreCaseAndEtiquetasIn(String searchText, List<String> tags);

    Optional<Product> findFirstByNombreOrderByPrecioAsc(String nombre);

    List<Product> findAllByNombre(String nombre);

    @Query(value = "{$group: {_id: '$nombre', minPrice: {$min: '$precio'}}}")
    List<Product> findAllCheapestProducts();

   
    List<Product> findByTagsIn(List<String> tags);
}