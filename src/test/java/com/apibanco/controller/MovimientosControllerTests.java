package com.apibanco.controller;

import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.apibanco.entity.Cliente;
import com.apibanco.entity.Cuenta;
import com.apibanco.entity.Movimientos;
import com.apibanco.exception.BusinessException;
import com.apibanco.repository.ICuentaRepo;
import com.apibanco.repository.IMovimientosRepo;
import com.apibanco.service.CuentaServiceImpl;
import com.apibanco.service.IClienteService;
import com.apibanco.service.ICuentaService;
import com.apibanco.service.IMovimientoService;
import com.apibanco.service.MovimientoServiceImpl;
import com.apibanco.utilidades.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovimientosControllerTests {
		
	//Esta clase recibira el objeto simulado clienteRepo
	@InjectMocks
	private CuentaServiceImpl cuentaServiceImpl;	
	
	//objeto simulado o mock
	@Mock
	private ICuentaRepo cuentaRepo;	
	
	
	@InjectMocks
	private MovimientoServiceImpl movimientoServicioImpl;
	
	@Mock
	private IMovimientosRepo movimientoRepo;
	
	
	public void llenarObjetoMovimiento(Movimientos movimiento) {
		movimiento.setEstado("true");
		movimiento.setCreationDateTime(new Date());
		movimiento.setFecha(new Date());
		movimiento.setFkclienteid(1);
		movimiento.setFkcuentaid(2);
		movimiento.setSaldo(2000);
		movimiento.setTipomovimiento("RETIRO");
		movimiento.setValor(3000);		
	}
	
	@Test
	public void registrarMovimientoTest() {
		Movimientos esperado = new Movimientos();		
		llenarObjetoMovimiento(esperado);		
		Mockito.when(movimientoRepo.save(esperado)).thenReturn(esperado);		
		Movimientos resultado = movimientoServicioImpl.registrar(esperado);
		Assert.assertTrue(esperado.equals(resultado));	
		Mockito.verify(movimientoRepo).save(esperado);		
	}
	
	@Test
	public void actualizarMovimientoTest() {
		Movimientos esperado = new Movimientos();	
		esperado.setMovimientosid(1);
		llenarObjetoMovimiento(esperado);		
		Mockito.when(movimientoRepo.save(esperado)).thenReturn(esperado);		
		Movimientos resultado = movimientoServicioImpl.actualizar(esperado);
		Assert.assertTrue(esperado.equals(resultado));	
	}	
	
	@Test
	public void eliminarMovimientoTest() {
		int id=1;
		movimientoServicioImpl.eliminar(id);
		Mockito.verify(movimientoRepo).deleteById(id);		
	}	
	
	@Test
	public void obtenerMovimientoPorIdTest() {
		Movimientos movimiento = new Movimientos();	
		movimiento.setMovimientosid(1);
		Optional<Movimientos> esperado =  Optional.of(movimiento);		
		Mockito.when(movimientoRepo.findById(movimiento.getMovimientosid() )).thenReturn(esperado);
		Optional<Movimientos> resultado = movimientoServicioImpl.findById(movimiento.getMovimientosid());
		Assert.assertTrue(esperado.equals(resultado));	
		Mockito.verify(movimientoRepo).findById(movimiento.getMovimientosid());		
	}	
	
	@Test
	public void validarValorSaldoMenorValorMovimientoTest() {		
		Cuenta objCuenta = new Cuenta();
		objCuenta.setSaldoinicial(500); 
		Movimientos movimiento = new Movimientos();
		movimiento.setValor(300);
		movimiento.setFkcuentaid(3);
		
		Cuenta cuenta = new Cuenta();	
		cuenta.setCuentaid(1);
		Optional<Cuenta> esperadoCuenta =  Optional.of(cuenta);		
		Mockito.when(cuentaRepo.findById(movimiento.getFkcuentaid())).thenReturn(esperadoCuenta);
		Optional<Cuenta> resultadoCuenta = cuentaServiceImpl.findById(cuenta.getCuentaid());		
		
		Optional<Movimientos> esperadoMovimiento =  Optional.of(movimiento);		
		Mockito.when(movimientoRepo.findById(movimiento.getMovimientosid() )).thenReturn(esperadoMovimiento);
		Optional<Movimientos> resultadoMovimiento = movimientoServicioImpl.findById(movimiento.getMovimientosid());
		
		Assert.assertTrue(esperadoCuenta.equals(resultadoCuenta));	
		Assert.assertTrue(esperadoMovimiento.equals(resultadoMovimiento));		
		Assert.assertTrue(esperadoCuenta.get().getSaldoinicial() <  esperadoMovimiento.get().getSaldo());			
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
