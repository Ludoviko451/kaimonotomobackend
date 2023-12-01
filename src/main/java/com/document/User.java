package com.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

	@Id
	private String id;
	private String nombre;
	private String direccion;
	private String paginaWeb;
	private boolean estado = true;
	private List<Product> productos;
	
	public List<Product> getProductos() {
		return productos;
	}
	public void setProductos(List<Product> productos) {
		this.productos = productos;
	}
	public User(String nombre, String direccion, String paginaWeb, boolean estado) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.paginaWeb = paginaWeb;
	}
	public User() {

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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPaginaWeb() {
		return paginaWeb;
	}
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
