package com.apibanco.service;

import java.util.Optional;

import com.apibanco.entity.Cuenta;

public interface ICuentaService {

	void registrar(Cuenta cuenta);
	void actualizar(Cuenta cuenta);
	public void eliminar(int id);
	Optional<Cuenta> findById(int id);	
	
}
