package com.apibanco.controller;

import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.apibanco.entity.Cliente;
import com.apibanco.entity.Cuenta;
import com.apibanco.entity.Movimientos;
import com.apibanco.service.IClienteService;
import com.apibanco.service.ICuentaService;
import com.apibanco.service.IMovimientoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovimientosControllerTests {

	
	@Mock
	private ICuentaService iCuentaService;
	
	@Mock
	private IClienteService iClienteService;	
	
	@Mock
	private IMovimientoService iMovimientoService;
	
	
	@Test
	public void registrarMovimientoTest() {
		Movimientos movimiento = new Movimientos();		
		movimiento.setEstado("true");
		movimiento.setCreationDateTime(new Date());
		movimiento.setFecha(new Date());
		movimiento.setFkclienteid(1);
		movimiento.setFkcuentaid(2);
		movimiento.setSaldo(2000);
		movimiento.setTipomovimiento("RETIRO");
		movimiento.setValor(3000);		
		
		Mockito.when(iMovimientoService.registrar(movimiento)).thenReturn(movimiento);		
		Movimientos resultadoExperado = iMovimientoService.registrar(movimiento);
		Assert.assertTrue(movimiento.equals(resultadoExperado));	
	}
	
	@Test
	public void actualizarMovimientoTest() {
		Movimientos movimiento = new Movimientos();	
		movimiento.setMovimientosid(1);
		movimiento.setEstado("true");
		movimiento.setCreationDateTime(new Date());
		movimiento.setFecha(new Date());
		movimiento.setFkclienteid(1);
		movimiento.setFkcuentaid(2);
		movimiento.setSaldo(2000);
		movimiento.setTipomovimiento("RETIRO");
		movimiento.setValor(3000);		
		
		Mockito.when(iMovimientoService.actualizar(movimiento)).thenReturn(movimiento);		
		Movimientos resultadoExperado = iMovimientoService.actualizar(movimiento);
		Assert.assertTrue(movimiento.equals(resultadoExperado));	
	}	
	
	@Test
	public void validarExisteClientePorIdTest() {	
		Cliente objCliente = new Cliente();
		objCliente.setClienteid(9);		 
		Optional<Cliente> cliente =  Optional.of(objCliente);		
		Mockito.when(iClienteService.findById(objCliente.getClienteid())).thenReturn(cliente);
		Optional<Cliente> resultadoExperado = iClienteService.findById(objCliente.getClienteid());
		Assert.assertTrue(cliente.equals(resultadoExperado));
	}	
		
	@Test
	public void validarExisteCuentaPorIdTest() {	
		Cuenta objCuenta = new Cuenta();
		objCuenta.setCuentaid(9); 
		Optional<Cuenta> cuenta =  Optional.of(objCuenta);		
		Mockito.when(iCuentaService.findById(objCuenta.getCuentaid())).thenReturn(cuenta);
		Optional<Cuenta> resultadoExperado = iCuentaService.findById(objCuenta.getCuentaid());
		Assert.assertTrue(cuenta.equals(resultadoExperado));
	}	
	
	@Test
	public void validarValorSaldoCeroTest() {		
		Cuenta objCuenta = new Cuenta();
		objCuenta.setSaldoinicial(200); 
		Optional<Cuenta> cuenta =  Optional.of(objCuenta);		
		Mockito.when(iCuentaService.findById(Mockito.anyInt()) ).thenReturn(cuenta);
		Assert.assertTrue(cuenta.get().getSaldoinicial() != 0);		
	}
	
	@Test
	public void validarValorSaldoMenorValorMovimientoTest() {		
		Cuenta objCuenta = new Cuenta();
		objCuenta.setSaldoinicial(200); 
		Movimientos movimiento = new Movimientos();
		movimiento.setValor(300);
		Optional<Cuenta> cuenta =  Optional.of(objCuenta);		
		Mockito.when(iCuentaService.findById(Mockito.anyInt()) ).thenReturn(cuenta);
		Assert.assertTrue(cuenta.get().getSaldoinicial() < movimiento.getValor() );		
	}	

	@Test
	public void validarExisteMovimientoPorIdTest() {  
		Movimientos objMovimiento = new Movimientos();
		objMovimiento.setMovimientosid(5);
		Optional<Movimientos> movimiento =  Optional.of(objMovimiento);		
		Mockito.when(iMovimientoService.findById(objMovimiento.getMovimientosid())).thenReturn(movimiento);
		Assert.assertTrue(objMovimiento != null);   	
    }
	
	@Test
	public void nuevoSaldoRestiroTest() {
		Movimientos objMovimiento = new Movimientos();
		Cuenta cuenta = new Cuenta();
		cuenta.setSaldoinicial(2);
		objMovimiento.setSaldo(6);
		float valorCalculado = cuenta.getSaldoinicial() + objMovimiento.getSaldo();
		float valorEsperado = 8;
		Assert.assertTrue(valorCalculado == valorEsperado);		
	}
	
	@Test
	public void nuevoSaldoDepositoTest() {
		Movimientos objMovimiento = new Movimientos();
		Cuenta cuenta = new Cuenta();
		cuenta.setSaldoinicial(200);
		objMovimiento.setSaldo(50);
		float valorCalculado = cuenta.getSaldoinicial() - objMovimiento.getSaldo();
		float valorEsperado = 150;
		Assert.assertTrue(valorCalculado == valorEsperado);		
	}	
	
}
