package com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.repository.ProductRepository;
import com.repository.UserRepository;
import com.document.User;
import com.services.ProductService;
import com.document.Product;

@CrossOrigin(origins = "*", maxAge = 4800)
@RequestMapping("/api/v1")
@RestController
public class ProductController {

    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private MongoTemplate mongoTemplate;



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{idTienda}/products")
    public Product crearProducto(@PathVariable String idTienda, @RequestBody Product producto) {
        User tienda = userRepository.findById(idTienda)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
        producto.setIdTienda(tienda.getId());
        return productRepository.save(producto);
    }

    @GetMapping("/products")
    	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}

    @GetMapping("/{idTienda}/products/{idProduct}")
    public Product getById(@PathVariable String idTienda, @PathVariable String idProduct) {
            User tienda = userRepository.findById(idTienda)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));

            Product producto = productRepository.findById(idProduct)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (!producto.getIdTienda().equals(idTienda)) {
                throw new RuntimeException("El producto no pertenece a esta tienda");
            }

            return producto; // Devuelve el producto obtenido y verificado
        }



    @PutMapping("/{idTienda}/products/{idProduct}")
    public Product getById(@PathVariable String idProduct, @PathVariable String idTienda, @RequestBody Product UpdatedProduct) {
        User tienda = userRepository.findById(idTienda)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
    
        Product producto = productRepository.findById(idProduct)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
        if (!producto.getIdTienda().equals(idTienda)) {
            throw new RuntimeException("El producto no pertenece a esta tienda");
        }
    
        if (producto != null) {
            if (UpdatedProduct.getNombre() != null) {
                producto.setNombre(UpdatedProduct.getNombre());
            }
            if (UpdatedProduct.getPrecio() != 0.0) {
                producto.setPrecio(UpdatedProduct.getPrecio());
            }
            if (UpdatedProduct.getDescripcion() != null) {
                producto.setDescripcion(UpdatedProduct.getDescripcion());
            }
            if (UpdatedProduct.getImagen() != null) {
                producto.setImagen(UpdatedProduct.getImagen());
            }
        }
        return productRepository.save(producto);
    }

    @DeleteMapping("/{idTienda}/products/{idProduct}")
    public ResponseEntity<String> delete(@PathVariable String idProduct, @PathVariable String idTienda) {
        User tienda = userRepository.findById(idTienda)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
    
        Product producto = productRepository.findById(idProduct)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
        if (!producto.getIdTienda().equals(idTienda)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El producto no pertenece a esta tienda");
        }
    
        productRepository.deleteById(idProduct);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }
    


    
    @GetMapping("/{idTienda}/products")
    public List<Product> getAllProductsByStoreid(@PathVariable String idTienda) {
        try {
            return productRepository.findProductsByIdTienda(idTienda);
        } catch (Exception e) {
            // Manejar la excepción si la tienda no existe
            return Collections.emptyList(); // Lista vacía
        }
    }

    // @GetMapping("/search")
    // public ResponseEntity<Product> searchProducts(
    //         @RequestParam(name = "text", required = false) String searchText,
    //         @RequestParam(name = "tags", required = false) List<String> tags) {

    //     if (searchText != null && tags != null && !tags.isEmpty()) {
    //         // Si ambos están presentes, realiza una búsqueda combinada
    //         Product cheapestProductCombined = productService.findCheapestProductCombined(searchText, tags);
    //         if (cheapestProductCombined != null) {
    //             return ResponseEntity.ok(cheapestProductCombined);
    //         } else {
    //             return ResponseEntity.notFound().build();
    //         }
    //     } else if (searchText != null) {
    //         // Si solo hay texto, realiza una búsqueda por texto
    //         Product cheapestProductByText = productService.findCheapestProductByText(searchText);
    //         if (cheapestProductByText != null) {
    //             return ResponseEntity.ok(cheapestProductByText);
    //         } else {
    //             return ResponseEntity.notFound().build();
    //         }
    //     } else if (tags != null && !tags.isEmpty()) {
    //         // Si solo hay etiquetas, realiza una búsqueda por etiquetas
    //         Product cheapestProductByTags = productService.findCheapestProductByTags(tags);
    //         if (cheapestProductByTags != null) {
    //             return ResponseEntity.ok(cheapestProductByTags);
    //         } else {
    //             return ResponseEntity.notFound().build();
    //         }
    //     } else {
    //         // Si no se proporciona ningún parámetro, devuelve un error
    //         return ResponseEntity.badRequest().build();
    //     }
    // }

    @GetMapping("/byName/{name}")
    public Optional<Product> findCheapestProductByName(@PathVariable String name) {
        return productRepository.findFirstByNombreOrderByPrecioAsc(name);
    }

    @GetMapping("/allbyName/{name}")
    public List<Product> findProductsByName(@PathVariable String name) {
        return productRepository.findAllByNombre(name);
    }
}




    

