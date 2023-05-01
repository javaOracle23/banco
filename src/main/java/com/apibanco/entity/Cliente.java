package com.apibanco.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
//principio solid  abierto/cerrado 
//la clase Persona esta abierta para extenderse o asociarse y cerrada para modificarse 
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="clienteid")
	private int clienteid;	
	
	@Column(name = "contrasena")
	private String contrasena;
	
	@Column(name = "estado")
	private String estado;
		
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkpersonaid", referencedColumnName = "personaid")
	private Persona persona;
	
	
	
	public Cliente() {
		super();
	}
	
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public int getClienteid() {
		return clienteid;
	}
	public void setClienteid(int clienteid) {
		this.clienteid = clienteid;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
		
}
