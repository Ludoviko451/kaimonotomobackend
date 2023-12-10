package com.document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.document.User;

import java.util.List;
import java.util.UUID;

@Document(collection = "productos")
public class Product {


    @Id
    private String id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String imagen;
    private String idTienda;
    private List<String> tags;
    // Constructores, getters, setters, etc.







    // Constructor sin argumentos
    public Product() {
        this.id = UUID.randomUUID().toString(); // Asignar un ID único al crear un nuevo producto
    }

    // Constructor con argumentos
    public Product(String nombre, double precio, String descripcion, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.id = UUID.randomUUID().toString(); // Asignar un ID único al crear un nuevo producto
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(String idTienda) {
        this.idTienda = idTienda;
    }

    public List<String> getEtiquetas() {
        return tags;
    }

    public void setEtiquetas(List<String> tags) {
        this.tags = tags;
    }
}
