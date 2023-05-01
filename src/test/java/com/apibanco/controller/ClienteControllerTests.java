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

import com.apibanco.entity.Cliente;
import com.apibanco.entity.Persona;
import com.apibanco.repository.IClienteRepo;
import com.apibanco.repository.IPersonaRepo;
import com.apibanco.service.ClienteServiceImpl;
import com.apibanco.service.IPersonaService;
import com.apibanco.service.PersonaServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteControllerTests {

	@Mock
	private IPersonaService iPersonaService;
	
	//Esta clase recibira el objeto simulado clienteRepo
	@InjectMocks
	private ClienteServiceImpl clienteServiceImpl;	
	
	//objeto simulado o mock
	@Mock
	private IClienteRepo clienteRepo;
	
	//Esta clase recibira el objeto simulado clienteRepo
	@InjectMocks
	private PersonaServiceImpl personaServiceImpl;	
	
	//objeto simulado o mock
	@Mock
	private IPersonaRepo personaRepo;
	
	@Test
	public void contextLoads() {
	}
	
	public void llenarObjetoCliente(Cliente objCliente, Persona persona) {		
		objCliente.setContrasena("1234");
		objCliente.setEstado("true");				
		persona.setPersonaid(2);
		persona.setDireccion("xdd");
		persona.setEdad(25);
		persona.setGenero("M");
		persona.setNombre("DDDD");
		persona.setIdentificacion(1020);
		persona.setTelefono(211234);
		objCliente.setPersona(persona);		
	}
	
	@Test
	public void registrarClienteTest() {
		Cliente objCliente = new Cliente();	
		Persona persona = new Persona();
		llenarObjetoCliente(objCliente, persona);
		Mockito.when((clienteRepo.save(objCliente))).thenReturn(objCliente);		
		Cliente resultado = clienteServiceImpl.registrar(objCliente);
		Assert.assertTrue(objCliente.equals(resultado));
		Mockito.verify(clienteRepo).save(objCliente);		
	}
	
	
	@Test
	public void actualizarClienteTest() {
		Cliente objCliente = new Cliente();	
		objCliente.setClienteid(1);
		Persona persona = new Persona();
		persona.setPersonaid(2);
		llenarObjetoCliente(objCliente, persona);					
		Mockito.when(clienteRepo.save(objCliente)).thenReturn(objCliente);
		Cliente resultado = clienteServiceImpl.registrar(objCliente);
		Assert.assertTrue(objCliente.equals(resultado));
		Mockito.verify(clienteRepo).save(objCliente);		
	}
	
	@Test
	public void eliminarClienteTest() {
		int id=1;
		clienteServiceImpl.eliminar(id);	
		Mockito.verify(clienteRepo).deleteById(id);		
	}	
	
	@Test
	public void obtenerClientePorIdTest() {
		Cliente objCliente = new Cliente();
		objCliente.setClienteid(9);	
		Optional<Cliente> cliente =  Optional.of(objCliente);		
		Mockito.when(clienteRepo.findById(objCliente.getClienteid())).thenReturn(cliente);
		Optional<Cliente> resultado = clienteServiceImpl.findById(objCliente.getClienteid());
		Assert.assertTrue(cliente.equals(resultado));	
		Mockito.verify(clienteRepo).findById(objCliente.getClienteid());		
	}
	
	@Test
	public void obtenerPersonaPorIdTest() {	
		Persona objPersona = new Persona();
		objPersona.setPersonaid(2);
		Optional<Persona> persona = Optional.of(objPersona);		
		Mockito.when(personaRepo.findById(objPersona.getPersonaid())).thenReturn(persona);
		Optional<Persona> resultado = personaServiceImpl.findById(objPersona.getPersonaid());
		Assert.assertTrue(persona.equals(resultado));	
		Mockito.verify(personaRepo).findById(objPersona.getPersonaid());		
	}		

}
