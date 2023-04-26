package com.apibanco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apibanco.entity.Cuenta;

@Repository
public interface ICuentaRepo extends JpaRepository<Cuenta, Integer> {	
	Optional<Cuenta> findById(int id);
}
