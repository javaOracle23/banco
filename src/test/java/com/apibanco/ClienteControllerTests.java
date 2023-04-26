package com.apibanco;

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
    public void validarConsultaPersonaPorId() {
		Optional<Persona> persona = Optional.of(new Persona(9));	
        Mockito.when(iPersonaService.findById(Mockito.anyInt())).thenReturn(persona);
        Optional<Persona> personas = iPersonaService.findById(Mockito.anyInt());
        Assert.assertEquals(9, personas.get().getPersonaid() );
    }
	
	
	@Test
    public void validarConsultaClientePorContraseña() {
		Optional<Cliente> cliente = Optional.of(new Cliente("1234"));	
        Mockito.when(iClienteService.findById(Mockito.anyInt())).thenReturn(cliente);
        Optional<Cliente> Clientes = iClienteService.findById(Mockito.anyInt());
        Assert.assertEquals("1234", Clientes.get().getContrasena() );
    }
	


}
