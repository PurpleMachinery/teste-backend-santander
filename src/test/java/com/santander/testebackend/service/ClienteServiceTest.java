package com.santander.testebackend.service;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.repository.ClienteRepository;
import com.santander.testebackend.service.impl.ClienteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    private ClienteRepository clienteRepository;

    @Mock
    private IClienteService clienteService;

    @Before
    public void init() {
        clienteRepository = mock(ClienteRepository.class);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    public void deveRetonarClienteSalvo() {
        when(clienteRepository.save(any())).thenReturn(new Cliente());

        clienteService.salvarCliente(new Cliente());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void deveListarUsuariosSalvos() {
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente());

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        clienteService.listarClientes();

        verify(clienteRepository, times(1)).findAll();
    }
}
