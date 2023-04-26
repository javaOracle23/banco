package com.apibanco.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apibanco.entity.Movimientos;
import com.apibanco.repository.IMovimientosRepo;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

	private static Logger LOG = LoggerFactory.getLogger(MovimientoServiceImpl.class);
		
	@Autowired	
	private IMovimientosRepo movimientoRepo;
	
	public void registrar(Movimientos movimiento) {
		movimientoRepo.save(movimiento);
		
	}

	public void actualizar(Movimientos movimiento) {
		movimientoRepo.save(movimiento);
		
	}

	public void eliminar(int id) {
		movimientoRepo.deleteById(id);  
		
	}
	
	public boolean existsById(int id) {
		return movimientoRepo.existsById(id);
	}
	
	@Override
	public  Optional<Movimientos> findById(int id) {
		return movimientoRepo.findById(id);		
	}

	@Override
	public List<Movimientos> findAllByFechaBetween(Date init,Date fin) {
		return movimientoRepo.findAllByFechaBetween(init,fin);
	}
	


}
