package com.santander.testebackend.service;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.repository.ClienteRepository;
import com.santander.testebackend.service.impl.ClienteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public void deveTestarClienteModel() {
        when(clienteRepository.save(any())).thenReturn(parametroCliente());

        Cliente retorno = clienteService.salvarCliente(new Cliente());

        Cliente parametroCliente = parametroCliente();

        Assertions.assertEquals(retorno.getId(), parametroCliente.getId());
        Assertions.assertEquals(retorno.getSaldo(), parametroCliente.getSaldo());
        Assertions.assertEquals(retorno.getNome(), parametroCliente.getNome());
        Assertions.assertEquals(retorno.getNumeroConta(), parametroCliente.getNumeroConta());
        Assertions.assertEquals(retorno.isPlanoExclusive(), parametroCliente.isPlanoExclusive());
        Assertions.assertEquals(retorno.getDataNascimento(), parametroCliente.getDataNascimento());
    }

    @Test
    public void deveRetonarClienteSalvo() {
        when(clienteRepository.save(any())).thenReturn(parametroCliente());

        Cliente retorno = clienteService.salvarCliente(new Cliente());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        Assert.assertNotNull(retorno);
    }

    @Test
    public void deveListarUsuariosSalvos() {
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente());

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        clienteService.listarClientes();

        verify(clienteRepository, times(1)).findAll();
    }

    private static Cliente parametroCliente() {
        Cliente cliente = new Cliente();
        cliente.setSaldo(BigDecimal.TEN);
        cliente.setId(1);
        cliente.setNome("ipsum lorem");
        cliente.setNumeroConta("111111");
        cliente.setPlanoExclusive(false);
        cliente.setDataNascimento(LocalDate.now());

        return cliente;
    }
}
