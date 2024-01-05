package com.santander.testebackend.controller;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.service.IClienteService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private IClienteService clienteService;

    @Test
    public void deveListarClientes() {
        Mockito.when(clienteService.salvarCliente(any())).thenReturn(new Cliente());

        ResponseEntity<Cliente> response = clienteController.cadastrarCliente(new Cliente());

        verify(clienteService, times(1)).salvarCliente(any());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void deveCadastrarCliente() {
        ResponseEntity<List<Cliente>> response = clienteController.listarClientes();

        verify(clienteService, times(1)).listarClientes();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }
}
