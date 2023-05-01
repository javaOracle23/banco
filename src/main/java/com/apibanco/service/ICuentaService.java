package com.apibanco.service;

import java.util.Optional;

import com.apibanco.entity.Cuenta;

public interface ICuentaService {

	Cuenta registrar(Cuenta cuenta);
	Cuenta actualizar(Cuenta cuenta);
	public void eliminar(int id);
	Optional<Cuenta> findById(int id);	
	
}
