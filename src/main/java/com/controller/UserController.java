package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.document.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.document.User;
import com.exception.ResourceNotFoundException;
import com.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
}


	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public User create(@RequestBody User tienda) {
        return userRepository.save(tienda);
    }
 
	@PutMapping("/{id}")
    public User update(@PathVariable String id, @RequestBody User tienda) {
        User tiendaFromDb = userRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        tiendaFromDb.setNombre(tienda.getNombre());
        tiendaFromDb.setDireccion(tienda.getDireccion());
        tiendaFromDb.setPaginaWeb(tienda.getPaginaWeb());

        return userRepository.save(tiendaFromDb);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        User tienda = userRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        userRepository.delete(tienda);

    }


	// @PostMapping("/users/{id}/addProducts")
	// public ResponseEntity<User> agregarProductos(@PathVariable String id, @RequestBody List<Product> nuevosProductos) {
	// 	User tienda = userRepository.findById(id)
	// 			.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada con ID: " + id));
	
	// 	List<Product> productosActuales = tienda.getProductos();
	
	// 	if (productosActuales == null) {
	// 		productosActuales = new ArrayList<>();
	// 	}
	
	// 	productosActuales.addAll(nuevosProductos);
	// 	tienda.setProductos(productosActuales);
	// 	userRepository.save(tienda);
	
	// 	return ResponseEntity.ok(tienda);
	// }
	

	// @PostMapping("/users/{id}/addProduct")
	// public ResponseEntity<User> addProduct(@PathVariable String id, @RequestBody Product nuevoProducto) {
	// 	User tienda = userRepository.findById(id)
	// 			.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada con ID: " + id));
	
	// 	List<Product> productosActuales = tienda.getProductos();
	
	// 	if (productosActuales == null) {
	// 		productosActuales = new ArrayList<>();
	// 	}
	
	// 	productosActuales.add(nuevoProducto);
	// 	tienda.setProductos(productosActuales);
	// 	userRepository.save(tienda);
	
	// 	return ResponseEntity.ok(tienda);
	// }
	

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
		String id = credentials.get("id");
	
		// Buscar el usuario por ID en lugar de nombre de usuario (o ID)
		User user = userRepository.findById(id).orElse(null);
		
		if (user != null && user.getId().equals(id)) {
			// Autenticación exitosa, devolver los datos del usuario
			return ResponseEntity.ok(user);
		} else {
			// Autenticación fallida
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
		}
	}

	// @DeleteMapping("/users/{userId}/products/{productId}")
	// public ResponseEntity<?> eliminarProducto(@PathVariable String userId, @PathVariable String productId) {
	// 	User tienda = userRepository.findById(userId)
	// 			.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada con ID: " + userId));
	
	// 	List<Product> productosActuales = tienda.getProductos();
	
	// 	if (productosActuales != null) {
	// 		productosActuales.removeIf(producto -> producto.getId().equals(productId));
	// 		tienda.setProductos(productosActuales);
	// 		userRepository.save(tienda);
	// 		return ResponseEntity.ok(tienda);
	// 	} else {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos para eliminar");
	// 	}
	// }
	
	// @PutMapping("/users/{userId}/products/{productId}")
	// public ResponseEntity<?> actualizarProducto(@PathVariable String userId, @PathVariable String productId, @RequestBody Product nuevoProducto) {
	// 	User tienda = userRepository.findById(userId)
	// 			.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada con ID: " + userId));
	
	// 	List<Product> productosActuales = tienda.getProductos();
	
	// 	if (productosActuales != null) {
	// 		for (Product producto : productosActuales) {
	// 			if (producto.getId().equals(productId)) {
	// 				if (nuevoProducto != null) {
	// 					if (nuevoProducto.getNombre() != null) {
	// 						producto.setNombre(nuevoProducto.getNombre());
	// 					}
	// 					if (nuevoProducto.getPrecio() != 0.0) {
	// 						producto.setPrecio(nuevoProducto.getPrecio());
	// 					}
	// 					if (nuevoProducto.getDescripcion() != null) {
	// 						producto.setDescripcion(nuevoProducto.getDescripcion());
	// 					}
	// 					if (nuevoProducto.getImagen() != null) {
	// 						producto.setImagen(nuevoProducto.getImagen());
	// 					}
	
	// 					// Guardar la tienda actualizada
	// 					userRepository.save(tienda);
	// 					return ResponseEntity.ok(producto);
	// 				} else {
	// 					return ResponseEntity.badRequest().body("El objeto nuevoProducto es nulo");
	// 				}
	// 			}
	// 		}
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto con ID: " + productId);
	// 	} else {
	// 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos para actualizar");
	// 	}
	// }

	
}
