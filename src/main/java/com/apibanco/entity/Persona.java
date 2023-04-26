package com.apibanco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "persona")
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int personaid;
	
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "genero")
	private String genero;
	@Column(name = "edad")
	private int edad;
	@Column(name = "identificacion")
	private int identificacion;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "telefono")
	private int telefono;
	
	//@OneToOne(mappedBy="persona")
//	@OneToOne 
//    @MapsId
//    @JoinColumn(name = "client_cpf")
//	private Cliente cliente;
	
	
	
	
	public int getPersonaid() {
		return personaid;
	}
	public Persona(int personaid) {
		super();
		this.personaid = personaid;
	}
	public void setPersonaid(int personaid) {
		this.personaid = personaid;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public int getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	
	
	
	
	
	
	
	
	
	
}
