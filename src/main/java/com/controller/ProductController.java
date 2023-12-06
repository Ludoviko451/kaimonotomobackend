package com.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.repository.ProductRepository;
import com.repository.UserRepository;
import com.document.User;
import com.document.Product;

@CrossOrigin(origins = "*", maxAge = 4800)
@RequestMapping("/api/v1")
@RestController
public class ProductController {

    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  UserRepository userRepository;

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

    

}
