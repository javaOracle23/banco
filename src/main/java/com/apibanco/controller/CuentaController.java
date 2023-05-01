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
import com.apibanco.entity.Cuenta;
import com.apibanco.exception.BusinessException;
import com.apibanco.service.IClienteService;
import com.apibanco.service.ICuentaService;
import com.apibanco.utilidades.Constantes;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CuentaController.class);
	
	@Autowired	
	private ICuentaService iCuentaService;
	
	//La clase CuentaController utiliza la interfaz IClienteService y desconoce su implementación.
	//Principio de inversión de dependencias o  inyección de dependencias en spring
	@Autowired	
	private IClienteService iClienteService;
	
	
	public void validarExiteClientePorId(int id) {
		if(!iClienteService.findById(id).isPresent()) {
			throw new BusinessException(Constantes.WS_REST_MSG_CLIENTE_NO_EXISTE);   		
    	}  
	}	
	
	
	public void validarExiteCuentaPorId(int id) {    	
    	if(!iCuentaService.findById(id).isPresent()) {
    		throw new BusinessException(Constantes.WS_REST_MSG_CUENTA_NO_EXISTE);    	
    	}	
	}
	
	
	@PostMapping
	public ResponseEntity<?> insertar(@RequestBody Cuenta cuenta) {
		validarExiteClientePorId(cuenta.getCuentaid());
		try {
			iCuentaService.registrar(cuenta);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		}		
		return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_GUARDAR, HttpStatus.OK);    	
	}
	
	
    @PutMapping
	public ResponseEntity<?> actualizar(@RequestBody Cuenta cuenta) {    	
    	validarExiteCuentaPorId(cuenta.getCuentaid());
    	validarExiteClientePorId(cuenta.getFkclienteid() );
    	if(!iClienteService.findById(cuenta.getFkclienteid()).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_CLIENTE_NO_EXISTE, HttpStatus.FOUND);    	
    	}   
    	try {
    		iCuentaService.actualizar(cuenta);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		}	    	
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_ACTULIZAR, HttpStatus.OK);
	}
    
    
    
    @DeleteMapping(value="/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
    	validarExiteCuentaPorId(id);
    	try {
    		iCuentaService.eliminar(id);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		}	   	
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_ELIMINAR, HttpStatus.OK);
	}
	
}
