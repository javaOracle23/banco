package com.apibanco.service;

import java.util.Optional;

import com.apibanco.entity.Persona;

public interface IPersonaService {
	

	public boolean existsById(int id);
	
	void registrar(Persona persona);
	
	Optional<Persona> findById(int id);
	
}
