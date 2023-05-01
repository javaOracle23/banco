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
import com.apibanco.entity.Persona;
import com.apibanco.service.IClienteService;
import com.apibanco.service.IPersonaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteControllerTests {

	@Mock
	private IPersonaService iPersonaService;
	
	@Mock
	private IClienteService iClienteService;	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void registrarClienteTest() {
		Cliente objCliente = new Cliente();		
		objCliente.setContrasena("1234");
		objCliente.setEstado("true");	
		
		Persona persona = new Persona();
		persona.setPersonaid(2);
		persona.setDireccion("xdd");
		persona.setEdad(25);
		persona.setGenero("M");
		persona.setNombre("DDDD");
		persona.setIdentificacion(1020);
		persona.setTelefono(211234);
		objCliente.setPersona(persona);		
		
		Mockito.when((iClienteService.registrar(objCliente))).thenReturn(objCliente);		
		Cliente resultadoExperado = iClienteService.registrar(objCliente);
		Assert.assertTrue(objCliente.equals(resultadoExperado));	
	}
	
	
	@Test
	public void actualizarClienteTest() {
		Cliente objCliente = new Cliente();	
		objCliente.setClienteid(1);
		objCliente.setContrasena("1234");
		objCliente.setEstado("true");	
		
		Persona persona = new Persona();
		persona.setPersonaid(2);
		persona.setDireccion("xdd");
		persona.setEdad(25);
		persona.setGenero("M");
		persona.setNombre("DDDD");
		persona.setIdentificacion(1020);
		persona.setTelefono(211234);
		objCliente.setPersona(persona);			
		Mockito.when(iClienteService.actualizar(objCliente)).thenReturn(objCliente);
		Assert.assertNotNull(objCliente);		
	}
	
	@Test
	public void eliminarClienteTest() {
		int id=1;
		iClienteService.eliminar(id);
		Mockito.verify(iClienteService).eliminar(id);		
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
	public void validarExistePersonaPorIdTest() {	
		Persona objPersona = new Persona();
		objPersona.setPersonaid(2);
		Optional<Persona> persona = Optional.of(objPersona);		
		Mockito.when(iPersonaService.findById(objPersona.getPersonaid())).thenReturn(persona);
		Optional<Persona> resultadoExperado = iPersonaService.findById(objPersona.getPersonaid());
		Assert.assertTrue(persona.equals(resultadoExperado));		
	}		

}
