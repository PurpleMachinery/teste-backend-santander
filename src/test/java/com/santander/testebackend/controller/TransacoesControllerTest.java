package com.santander.testebackend.controller;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.model.HistoricoTransacao;
import com.santander.testebackend.model.Transacao;
import com.santander.testebackend.service.IClienteService;
import com.santander.testebackend.service.ITransacoesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransacoesControllerTest {

    @InjectMocks
    private TransacoesController transacoesController;

    @Mock
    private ITransacoesService transacoesService;

    @Test
    public void deveConsultarTransacao() {
        ResponseEntity<List<HistoricoTransacao>> response = transacoesController.consultarTransacaoPorData(LocalDate.now());

        verify(transacoesService, times(1)).listarTransacoes(any());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void deveDepositarValor() {
        when(transacoesService.depositarValor(any())).thenReturn(new Cliente());

        ResponseEntity<Cliente> response = transacoesController.depositarValor(new Transacao());

        verify(transacoesService, times(1)).depositarValor(any());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void deveSacarValor() throws Exception {
        when(transacoesService.sacarValor(any())).thenReturn(new Cliente());

        ResponseEntity<Cliente> response = transacoesController.sacarValor(new Transacao());

        verify(transacoesService, times(1)).sacarValor(any());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }
}
