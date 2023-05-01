package com.apibanco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apibanco.entity.Cliente;
//principio solid  abierto/cerrado 
//la clase JpaRepository esta abierta para extenderse y cerrada para modificarse 
//Abstract Factory es un patrón de diseño creacional
//La fábrica JpaRepository tiene metodos correspodientes a consultas basicas a BD
@Repository
public interface IClienteRepo  extends JpaRepository<Cliente, Integer> {
	
	Optional<Cliente> findById(int id);
	
}
