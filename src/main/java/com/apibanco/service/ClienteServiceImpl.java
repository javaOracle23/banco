package com.apibanco.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apibanco.entity.Cliente;
import com.apibanco.repository.IClienteRepo;

@Service
public class ClienteServiceImpl implements IClienteService {

	private static Logger LOG = LoggerFactory.getLogger(ClienteServiceImpl.class);
		
	@Autowired	
	private IClienteRepo clienteRepo;
	
	public void registrar(Cliente cliente) {
		clienteRepo.save(cliente);		
	}

	public void actualizar(Cliente cliente) {
		clienteRepo.save(cliente);		
	}

	public void eliminar(int id) {
		clienteRepo.deleteById(id); 		
	}
	
	public boolean existsById(int id) {
		return clienteRepo.existsById(id);
	}
	
	@Override
	public  Optional<Cliente> findById(int id) {
		return clienteRepo.findById(id);		
	}
	


}
