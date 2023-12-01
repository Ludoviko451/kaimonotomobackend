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


	@PostMapping("/users/{id}/addProducts")
	public ResponseEntity<User> agregarProductos(@PathVariable String id, @RequestBody List<Map<String, Object>> nuevosProductos) {
		User tienda = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tienda no encontrada con ID: " + id));
	
		List<Product> productosActuales = tienda.getProductos();
		if (productosActuales == null) {
			productosActuales = new ArrayList<>();
		}
	
		ObjectMapper objectMapper = new ObjectMapper();
		for (Map<String, Object> productoMap : nuevosProductos) {
			Product nuevoProducto = objectMapper.convertValue(productoMap, Product.class);
	
			// Verificar si la clave de la ID está presente en el mapa y asignarla manualmente al objeto Product
			if (productoMap.containsKey("id")) {
				String productId = productoMap.get("id").toString();
				nuevoProducto.setId(productId);
			}
	
			productosActuales.add(nuevoProducto);
		}
	
		tienda.setProductos(productosActuales);
		userRepository.save(tienda);
	
		return ResponseEntity.ok(tienda);
	}
	
	
	

	
}
