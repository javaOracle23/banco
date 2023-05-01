package com.apibanco.controller;

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
import com.apibanco.service.IClienteService;
import com.apibanco.service.ICuentaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CuentaControllerTests {

	
	@Mock
	private ICuentaService iCuentaService;
	
	@Mock
	private IClienteService iClienteService;	
	
	
	@Test
	public void registrarCuentaTest() {
		Cuenta cuenta = new Cuenta();		
		cuenta.setEstado("true");
		cuenta.setFkclienteid(2);
		cuenta.setNumerocuenta("23245");
		cuenta.setSaldoinicial(3000);
		cuenta.setTipocuenta("CORRIENTE");
		
		Mockito.when(iCuentaService.registrar(cuenta)).thenReturn(cuenta);		
		Cuenta resultadoExperado = iCuentaService.registrar(cuenta);
		Assert.assertTrue(cuenta.equals(resultadoExperado));	
	}
	
	
	@Test
	public void actualizarCuentaTest() {
		Cuenta cuenta = new Cuenta();	
		cuenta.setCuentaid(1);
		cuenta.setEstado("true");
		cuenta.setFkclienteid(2);
		cuenta.setNumerocuenta("23245");
		cuenta.setSaldoinicial(3000);
		cuenta.setTipocuenta("CORRIENTE");
		
		Mockito.when(iCuentaService.actualizar(cuenta)).thenReturn(cuenta);		
		Cuenta resultadoExperado = iCuentaService.actualizar(cuenta);
		Assert.assertTrue(cuenta.equals(resultadoExperado));	
	}
	
	@Test
	public void eliminarClienteTest() {
		int id=1;
		iCuentaService.eliminar(id);
		Mockito.verify(iCuentaService).eliminar(id);		
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
	
}
