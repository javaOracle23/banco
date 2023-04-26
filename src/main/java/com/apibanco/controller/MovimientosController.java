package com.apibanco.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apibanco.entity.Cliente;
import com.apibanco.entity.Cuenta;
import com.apibanco.entity.Movimientos;
import com.apibanco.exception.BusinessException;
import com.apibanco.model.ListaMovimientos;
import com.apibanco.service.IClienteService;
import com.apibanco.service.ICuentaService;
import com.apibanco.service.IMovimientoService;
import com.apibanco.utilidades.Constantes;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

	private static Logger LOGGER = LoggerFactory.getLogger(MovimientosController.class);
	
	@Autowired	
	private ICuentaService iCuentaService;
	
	@Autowired	
	private IClienteService iClienteService;
	
	@Autowired	
	private IMovimientoService iMovimientoService;
	
	
	
	public void nuevoSaldoCuenta(Movimientos movimiento,Optional<Cuenta> cuenta) {
		if(movimiento.getTipomovimiento().equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_DEPOSITO)) {
			movimiento.setSaldo(cuenta.get().getSaldoinicial());			
			cuenta.get().setSaldoinicial(cuenta.get().getSaldoinicial() + movimiento.getValor());	
		}else if(movimiento.getTipomovimiento().equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_RETIRO)) {
			movimiento.setSaldo(cuenta.get().getSaldoinicial());	
			cuenta.get().setSaldoinicial(Math.abs(cuenta.get().getSaldoinicial() - movimiento.getValor()));			
		}		
	}
	
	public ListaMovimientos obtenerListaDemovimientos(Movimientos movimiento) {
		Optional<Cuenta> cuenta = iCuentaService.findById(movimiento.getFkcuentaid());
		Optional<Cliente> cliente = iClienteService.findById(movimiento.getFkclienteid());
		ListaMovimientos objMov = new ListaMovimientos(movimiento.getFecha(), cliente.get().getPersona().getNombre(), cuenta.get().getNumerocuenta(), cuenta.get().getTipocuenta(),movimiento.getSaldo(),
				movimiento.getEstado(), movimiento.getValor());		
		if(movimiento.getTipomovimiento().equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_DEPOSITO)) {
			objMov.setSaldoDisponible(objMov.getSaldoInicial() + objMov.getMovimiento());				
		}else if(movimiento.getTipomovimiento().equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_RETIRO)) {
			objMov.setSaldoDisponible(objMov.getSaldoInicial() - objMov.getMovimiento());		
		}
		return objMov;
	}
	
	@GetMapping("/reportes/{dateinit}/{dateend}/{fkclienteid}")
    public ResponseEntity<?> consultaMovimientos(@PathVariable(name = "dateinit") String dateinit, @PathVariable(name = "dateend") String dateend, @PathVariable(name = "fkclienteid") int fkclienteid) throws ParseException {		
		List<Movimientos> lista = iMovimientoService.findAllByFechaBetween(new SimpleDateFormat("yyyy-MM-dd").parse(dateinit), new SimpleDateFormat("yyyy-MM-dd").parse(dateend)).stream()
				.filter(item -> item.getFkclienteid() == fkclienteid)
				.collect(Collectors.toList());
		List<ListaMovimientos> lisMovimientos =
				lista.stream()
				       .map(
				    		 movimiento -> {		    			             
				             return obtenerListaDemovimientos(movimiento);
				           })
				       .collect(Collectors.toList());		
        return new ResponseEntity(lisMovimientos, HttpStatus.OK);
    }	
	
	@PostMapping
	public ResponseEntity<?> insertar(@RequestBody Movimientos movimiento){		
		if(!iClienteService.findById(movimiento.getFkclienteid()).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_CLIENTE_NO_EXISTE, HttpStatus.FOUND);    	
    	}   
		if(!iCuentaService.findById(movimiento.getFkcuentaid()).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_CUENTA_NO_EXISTE, HttpStatus.FOUND);    	
    	}   		
		Optional<Cuenta> cuenta = iCuentaService.findById(movimiento.getFkcuentaid());	
		if((cuenta.get().getSaldoinicial() == Constantes.VALOR_CERO || cuenta.get().getSaldoinicial() < movimiento.getValor()) && movimiento.getTipomovimiento().equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_RETIRO)) {
			return new ResponseEntity(Constantes.WS_REST_MSG_SALDO_NO_DISPONIBLE, HttpStatus.BAD_REQUEST);  
		}		
		nuevoSaldoCuenta(movimiento, cuenta);		
		try {
			iCuentaService.actualizar(cuenta.get());
			iMovimientoService.registrar(movimiento);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		}	   	
		return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_GUARDAR, HttpStatus.OK);    	
	}
	
    @PutMapping
	public ResponseEntity<?> actualizar(@RequestBody Movimientos movimiento) { 
    	if(!iClienteService.findById(movimiento.getFkclienteid()).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_CLIENTE_NO_EXISTE, HttpStatus.FOUND);    	
    	}  
    	if(!iCuentaService.findById(movimiento.getFkcuentaid() ).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_CUENTA_NO_EXISTE, HttpStatus.FOUND);    	
    	} 
    	Optional<Cuenta> cuenta = iCuentaService.findById(movimiento.getFkcuentaid());	
		if((cuenta.get().getSaldoinicial() == Constantes.VALOR_CERO || cuenta.get().getSaldoinicial() < movimiento.getValor()) && movimiento.getTipomovimiento().equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_RETIRO)) {
			return new ResponseEntity(Constantes.WS_REST_MSG_SALDO_NO_DISPONIBLE, HttpStatus.BAD_REQUEST);  
		}	
    	nuevoSaldoCuenta(movimiento, cuenta);    	
    	try {
    		iCuentaService.actualizar(cuenta.get());
        	iMovimientoService.actualizar(movimiento);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		}	
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_ACTULIZAR, HttpStatus.OK);
	}
    
    @DeleteMapping(value="/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
    	if(!iMovimientoService.findById(id).isPresent()) {
    		return new ResponseEntity(Constantes.WS_REST_MSG_MOVIMIENTO_NO_EXISTE, HttpStatus.FOUND);    	
    	} 
    	try {
    		iMovimientoService.eliminar(id);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		}	    	
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_ELIMINAR, HttpStatus.OK);
	}
	
}
