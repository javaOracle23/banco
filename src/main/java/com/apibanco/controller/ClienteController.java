package com.apibanco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apibanco.entity.Cliente;
import com.apibanco.exception.BusinessException;
import com.apibanco.service.IClienteService;
import com.apibanco.service.IPersonaService;
import com.apibanco.utilidades.Constantes;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private static Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired	
	private IClienteService iClienteService;
	@Autowired	
	private IPersonaService iPersonaService;
	
    @PostMapping
	public ResponseEntity<?> insertar(@RequestBody Cliente cliente) throws Exception {   
    	try {
    		iClienteService.registrar(cliente);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		} 
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_GUARDAR, HttpStatus.OK);    	
	}
	
    @PutMapping
	public ResponseEntity<?> actualizar(@RequestBody Cliente cliente) {    	
    	if(!iPersonaService.findById(cliente.getPersona().getPersonaid()).isPresent() || !iClienteService.findById(cliente.getClienteid()).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_CLIENTE_NO_EXISTE, HttpStatus.FOUND);    	
    	} 
    	try {
    		iClienteService.actualizar(cliente);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		} 
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_ACTULIZAR, HttpStatus.OK);
	}
    
    @DeleteMapping(value="/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
    	if(!iClienteService.findById(id).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_CLIENTE_NO_EXISTE, HttpStatus.FOUND);    	
    	}   
    	try {
    		iClienteService.eliminar(id);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		} 
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_ELIMINAR, HttpStatus.OK);
	}
    
}
