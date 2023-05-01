package com.apibanco.controller;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.apibanco.entity.Cuenta;
import com.apibanco.repository.ICuentaRepo;
import com.apibanco.service.CuentaServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CuentaControllerTests {
	
	//Esta clase recibira el objeto simulado clienteRepo
	@InjectMocks
	private CuentaServiceImpl cuentaServiceImpl;	
	
	//objeto simulado o mock
	@Mock
	private ICuentaRepo cuentaRepo;	
	
	
	public void llenarObjetoCuenta(Cuenta cuenta) {		
		cuenta.setEstado("true");
		cuenta.setFkclienteid(2);
		cuenta.setNumerocuenta("23245");
		cuenta.setSaldoinicial(3000);
		cuenta.setTipocuenta("CORRIENTE");
	}
	
	@Test
	public void registrarCuentaTest() {
		Cuenta cuenta = new Cuenta();		
		llenarObjetoCuenta(cuenta);		
		Mockito.when(cuentaRepo.save(cuenta)).thenReturn(cuenta);		
		Cuenta resultado = cuentaServiceImpl.registrar(cuenta);
		Assert.assertTrue(cuenta.equals(resultado));
		Mockito.verify(cuentaRepo).save(cuenta);		
	}
	
	
	@Test
	public void actualizarCuentaTest() {
		Cuenta cuenta = new Cuenta();	
		cuenta.setCuentaid(1);
		llenarObjetoCuenta(cuenta);		
		Mockito.when(cuentaRepo.save(cuenta)).thenReturn(cuenta);		
		Cuenta resultado = cuentaServiceImpl.actualizar(cuenta);
		Assert.assertTrue(cuenta.equals(resultado));	
	}
	
	@Test
	public void eliminarCuentaTest() {
		int id=1;
		cuentaServiceImpl.eliminar(id);
		Mockito.verify(cuentaRepo).deleteById(id);		
	}	
		
	@Test
	public void obtenerCuentaPorIdTest() {
		Cuenta cuenta = new Cuenta();	
		cuenta.setCuentaid(1);
		Optional<Cuenta> esperado =  Optional.of(cuenta);		
		Mockito.when(cuentaRepo.findById(cuenta.getCuentaid())).thenReturn(esperado);
		Optional<Cuenta> resultado = cuentaServiceImpl.findById(cuenta.getCuentaid());
		Assert.assertTrue(esperado.equals(resultado));	
		Mockito.verify(cuentaRepo).findById(cuenta.getCuentaid());		
	}
	
	@Test
	public void validarValorSaldoCeroTest() {		
		Cuenta objCuenta = new Cuenta();
		objCuenta.setCuentaid(1);
		objCuenta.setSaldoinicial(200); 
		Optional<Cuenta> cuenta =  Optional.of(objCuenta);		
		Mockito.when(cuentaRepo.findById(objCuenta.getCuentaid()) ).thenReturn(cuenta);		
		Assert.assertTrue(cuenta.get().getSaldoinicial() != 0);		
	}
	
	
}
