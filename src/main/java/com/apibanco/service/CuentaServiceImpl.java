package com.apibanco.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apibanco.entity.Cuenta;
import com.apibanco.repository.ICuentaRepo;

@Service
public class CuentaServiceImpl implements ICuentaService{

	private static Logger LOG = LoggerFactory.getLogger(CuentaServiceImpl.class);
	
	@Autowired	
	private ICuentaRepo cuentaRepo;	
		
	public void registrar(Cuenta cuenta) {		
		cuentaRepo.save(cuenta);
	}

	public void actualizar(Cuenta cuenta) {		
		cuentaRepo.save(cuenta);
	}

	public void eliminar(int id) {		
		cuentaRepo.deleteById(id);  
	}

	@Override
	public Optional<Cuenta> findById(int id) {		
		return cuentaRepo.findById(id);
	}	
	
}
