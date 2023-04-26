package com.apibanco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apibanco.entity.Cliente;

@Repository
public interface IClienteRepo  extends JpaRepository<Cliente, Integer> {
	
	Optional<Cliente> findById(int id);
	
}
