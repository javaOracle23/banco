package com.apibanco.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	public void validarValorSaldoCero(Movimientos movimiento, Optional<Cuenta> cuenta) {		
		if(validarTipoDeMovimiento(movimiento.getTipomovimiento())) {
			if(cuenta.get().getSaldoinicial() == Constantes.VALOR_CERO) {
				throw new BusinessException(Constantes.WS_REST_MSG_SALDO_NO_DISPONIBLE);	
			}
		}
	}
	
	public void validarValorSaldoMenorValorMovimiento(Movimientos movimiento, Optional<Cuenta> cuenta) {		
		if(validarTipoDeMovimiento(movimiento.getTipomovimiento())) {
			if(cuenta.get().getSaldoinicial() < movimiento.getValor()) {
				throw new BusinessException(Constantes.WS_REST_MSG_SALDO_NO_DISPONIBLE);	
			}
		}
	}
	
	public void validarExisteMovimientoPorId(int id) {    	
    	if(!iMovimientoService.findById(id).isPresent()){
    		throw new BusinessException(Constantes.WS_REST_MSG_MOVIMIENTO_NO_EXISTE);    	
    	}   
    }
	
	 public boolean validarTipoDeMovimiento(String tipoMovimiento) {
		 boolean bTipoMovimiento = Constantes.VALOR_FALSE;
		 if(tipoMovimiento.equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_DEPOSITO)) {
			 bTipoMovimiento = Constantes.VALOR_FALSE;
		 }else if(tipoMovimiento.equalsIgnoreCase(Constantes.WS_REST_MSG_TIPO_MOVIMIENTO_RETIRO)) {
			 bTipoMovimiento = Constantes.VALOR_TRUE;			
		 }		
		 return bTipoMovimiento;
	 }
	 
	 public void validacionesGeneralesMovimientos(Movimientos movimiento,Optional<Cuenta> cuenta) {
		validarExiteClientePorId(movimiento.getFkclienteid());
		validarExiteCuentaPorId(movimiento.getFkcuentaid());
		validarValorSaldoCero(movimiento, cuenta);		
		validarValorSaldoMenorValorMovimiento(movimiento, cuenta);			
		nuevoSaldoCuentaPorTipoDeMovimiento(movimiento, cuenta);		
	}
	
	public void nuevoSaldoCuentaPorTipoDeMovimiento(Movimientos movimiento,Optional<Cuenta> cuenta) {
		if(!validarTipoDeMovimiento(movimiento.getTipomovimiento())) {
			movimiento.setSaldo(cuenta.get().getSaldoinicial());			
			cuenta.get().setSaldoinicial(cuenta.get().getSaldoinicial() + movimiento.getValor());	
		}else {
			movimiento.setSaldo(cuenta.get().getSaldoinicial());	
			cuenta.get().setSaldoinicial(Math.abs(cuenta.get().getSaldoinicial() - movimiento.getValor()));			
		}		
	}
	
	public ListaMovimientos obtenerListaDemovimientos(Movimientos movimiento) {
		Optional<Cuenta> cuenta = iCuentaService.findById(movimiento.getFkcuentaid());
		Optional<Cliente> cliente = iClienteService.findById(movimiento.getFkclienteid());
		ListaMovimientos objMov = new ListaMovimientos(movimiento.getFecha(), cliente.get().getPersona().getNombre(), 
				cuenta.get().getNumerocuenta(), cuenta.get().getTipocuenta(),movimiento.getSaldo(),
				movimiento.getEstado(), movimiento.getValor());		
		if(!validarTipoDeMovimiento(movimiento.getTipomovimiento())) {
			objMov.setSaldoDisponible(objMov.getSaldoInicial() + objMov.getMovimiento());				
		}else{
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
		Optional<Cuenta> cuenta = iCuentaService.findById(movimiento.getFkcuentaid());
		validacionesGeneralesMovimientos(movimiento, cuenta);
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
    	Optional<Cuenta> cuenta = iCuentaService.findById(movimiento.getFkcuentaid());
		validacionesGeneralesMovimientos(movimiento, cuenta);	
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
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id){
    	validarExisteMovimientoPorId(id);    	
    	try {
    		iMovimientoService.eliminar(id);
    	}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e.getCause());
		}	    	
    	return new ResponseEntity(Constantes.WS_REST_MSG_REGISTRO_ELIMINAR, HttpStatus.OK);
	}
	
}
