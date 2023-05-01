package com.apibanco.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.apibanco.entity.Movimientos;

public interface IMovimientoService {

	Movimientos registrar(Movimientos cuenta);
	Movimientos actualizar(Movimientos cuenta);
	public void eliminar(int id);
	Optional<Movimientos> findById(int id);
	List<Movimientos> findAllByFechaBetween(Date init,Date fin);
	
}
