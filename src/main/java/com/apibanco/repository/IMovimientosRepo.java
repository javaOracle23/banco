package com.apibanco.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apibanco.entity.Movimientos;

@Repository
public interface IMovimientosRepo extends JpaRepository<Movimientos, Integer> {

	Optional<Movimientos> findById(int id);
	
	public List<Movimientos> findAllByFechaBetween(Date init,Date fin);
	
//	@Query("select a from movimientos a where a.fecha <= :creationDateTime")
//    List<Movimientos> findAllWithCreationDateTimeBefore(
//      @Param("creationDateTime") Date creationDateTime);
	
}