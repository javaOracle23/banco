package com.apibanco.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apibanco.entity.Persona;
import com.apibanco.repository.IPersonaRepo;


@Service
public class PersonaServiceImpl implements IPersonaService {

	private static Logger LOG = LoggerFactory.getLogger(PersonaServiceImpl.class);

	@Autowired	
	private IPersonaRepo personaRepo;
	
	public void registrar(Persona persona) {
		personaRepo.saveAndFlush(persona);
		
	}
	
	public boolean existsById(int id) {
		return personaRepo.existsById(id);
	}

	@Override
	public  Optional<Persona> findById(int id) {
		return personaRepo.findById(id);		
	}
	
	
}
