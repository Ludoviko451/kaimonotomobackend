package com.services;
import com.repository.ProductRepository;

import org.springframework.stereotype.Service;

import com.document.Product;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findCheapestProductByText(String searchText) {
        List<Product> products = productRepository.findByNombreContainingIgnoreCase(searchText);
        if (products.isEmpty()) {
            return null;
        }
        return products.stream().min(Comparator.comparing(Product::getPrecio)).orElse(null);
    }

    public Product findCheapestProductByTags(List<String> tags) {
        List<Product> products = productRepository.findByEtiquetasIn(tags);
        if (products.isEmpty()) {
            return null;
        }
        return products.stream().min(Comparator.comparing(Product::getPrecio)).orElse(null);
    }

    public Product findCheapestProductCombined(String searchText, List<String> tags) {
        List<Product> products = productRepository.findByNombreContainingIgnoreCaseAndEtiquetasIn(searchText, tags);
        if (products.isEmpty()) {
            return null;
        }
        return products.stream().min(Comparator.comparing(Product::getPrecio)).orElse(null);
    }
}
