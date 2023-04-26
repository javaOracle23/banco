package com.apibanco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.apibanco.entity.Persona;


public interface IPersonaRepo extends JpaRepository<Persona, Integer> {

	Optional<Persona> findById(int id);
	
	
}
