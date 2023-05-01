package com.apibanco.service;

import java.util.Optional;

import com.apibanco.entity.Cliente;

public interface IClienteService {
	
	Cliente registrar(Cliente cliente);
	Cliente actualizar(Cliente cliente);
	public void eliminar(int id);
	public boolean existsById(int id);
	Optional<Cliente> findById(int id);

}
