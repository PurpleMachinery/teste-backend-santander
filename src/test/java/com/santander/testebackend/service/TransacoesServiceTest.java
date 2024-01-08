package com.santander.testebackend.service;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.model.HistoricoTransacao;
import com.santander.testebackend.model.Taxa;
import com.santander.testebackend.model.Transacao;
import com.santander.testebackend.repository.ClienteRepository;
import com.santander.testebackend.repository.HistoricoTransacaoRepository;
import com.santander.testebackend.repository.TaxaRepository;
import com.santander.testebackend.service.impl.TransacoesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransacoesServiceTest {

    private ClienteRepository clienteRepository;

    private HistoricoTransacaoRepository historicoTransacaoRepository;
    private TaxaRepository taxaRepository;

    @Mock
    private ITransacoesService transacoesService;

    @Before
    public void init() {
        clienteRepository = mock(ClienteRepository.class);
        taxaRepository = mock(TaxaRepository.class);
        historicoTransacaoRepository = mock(HistoricoTransacaoRepository.class);
        transacoesService = new TransacoesServiceImpl(clienteRepository, taxaRepository, historicoTransacaoRepository);
    }

    @Test
    public void deveDepositarValor() {
        Cliente mockCliente = new Cliente();
        mockCliente.setSaldo(BigDecimal.ZERO);

        Transacao mockTransacao = new Transacao();
        mockTransacao.setValor(BigDecimal.TEN);

        when(clienteRepository.findClienteByNumeroConta(any())).thenReturn(Optional.of(mockCliente));
        when(clienteRepository.save(any())).thenReturn(new Cliente());

        when(historicoTransacaoRepository.save(any())).thenReturn(new HistoricoTransacao());

        transacoesService.depositarValor(mockTransacao);

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(historicoTransacaoRepository, times(1)).save(any(HistoricoTransacao.class));
    }

    @Test
    public void deveSacarValor() throws Exception {
        Cliente mockCliente = new Cliente();
        mockCliente.setSaldo(new BigDecimal("1000"));

        Transacao mockTransacao = new Transacao();
        mockTransacao.setValor(new BigDecimal("301"));
        mockTransacao.setNumeroConta("123456");

        when(clienteRepository.findClienteByNumeroConta(any())).thenReturn(Optional.of(mockCliente));
        when(clienteRepository.save(any())).thenReturn(new Cliente());

        when(historicoTransacaoRepository.save(any())).thenReturn(new HistoricoTransacao());

        transacoesService.sacarValor(mockTransacao);

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(historicoTransacaoRepository, times(1)).save(any(HistoricoTransacao.class));

        mockTransacao.setValor(new BigDecimal("101"));

        transacoesService.sacarValor(mockTransacao);

        verify(clienteRepository, times(2)).save(any(Cliente.class));
        verify(historicoTransacaoRepository, times(2)).save(any(HistoricoTransacao.class));
    }

    @Test(expected = Exception.class)
    public void deveSacarValor_exception() throws Exception {
        Cliente mockCliente = new Cliente();
        mockCliente.setSaldo(new BigDecimal("0"));

        Transacao mockTransacao = new Transacao();
        mockTransacao.setValor(new BigDecimal("301"));
        mockTransacao.setNumeroConta("123456");

        when(clienteRepository.findClienteByNumeroConta(any())).thenReturn(Optional.of(mockCliente));

        transacoesService.sacarValor(mockTransacao);
    }

    @Test
    public void deveListarTransacoes() {
        transacoesService.listarTransacoes(LocalDate.parse("2024-01-05"));

        verify(historicoTransacaoRepository, times(1)).findAllByDataMovimentacao(any());
    }

    @Test
    public void deveCalcularValorSubtraidoSemTaxa() throws Exception {
        Cliente mockCliente = new Cliente();
        mockCliente.setPlanoExclusive(false);
        mockCliente.setSaldo(new BigDecimal("100"));

        Transacao mockTransacao = new Transacao();
        mockTransacao.setValor(new BigDecimal("100"));
        mockTransacao.setNumeroConta("123456");

        mockarListaDeTaxas();

        when(clienteRepository.findClienteByNumeroConta(any())).thenReturn(Optional.of(mockCliente));
        when(clienteRepository.save(any())).thenReturn(mockCliente);

        Cliente clienteRetorno = transacoesService.sacarValor(mockTransacao);

        Assertions.assertEquals(new BigDecimal("0"), clienteRetorno.getSaldo());
    }

    @Test
    public void deveCalcularValorSubtraidoTaxa4Porcento() throws Exception {
        Cliente mockCliente = new Cliente();
        mockCliente.setPlanoExclusive(false);
        mockCliente.setSaldo(new BigDecimal("106"));

        Transacao mockTransacao = new Transacao();
        mockTransacao.setValor(new BigDecimal("101"));
        mockTransacao.setNumeroConta("123456");

        mockarListaDeTaxas();

        when(clienteRepository.findClienteByNumeroConta(any())).thenReturn(Optional.of(mockCliente));
        when(clienteRepository.save(any())).thenReturn(mockCliente);

        Cliente clienteRetorno = transacoesService.sacarValor(mockTransacao);

        Assertions.assertEquals(new BigDecimal("0.96"), clienteRetorno.getSaldo().setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    public void deveCalcularValorSubtraidoTaxa10Porcento() throws Exception {
        Cliente mockCliente = new Cliente();
        mockCliente.setPlanoExclusive(false);
        mockCliente.setSaldo(new BigDecimal("400"));

        Transacao mockTransacao = new Transacao();
        mockTransacao.setValor(new BigDecimal("301"));
        mockTransacao.setNumeroConta("123456");

        mockarListaDeTaxas();

        when(clienteRepository.findClienteByNumeroConta(any())).thenReturn(Optional.of(mockCliente));
        when(clienteRepository.save(any())).thenReturn(mockCliente);

        Cliente clienteRetorno = transacoesService.sacarValor(mockTransacao);

        Assertions.assertEquals(new BigDecimal("68.9"), clienteRetorno.getSaldo().setScale(1, RoundingMode.HALF_EVEN));
    }

    @Test
    public void deveCalcularValorSubtraido_listaVazia() throws Exception {
        Cliente mockCliente = new Cliente();
        mockCliente.setPlanoExclusive(false);
        mockCliente.setSaldo(new BigDecimal("400"));

        Transacao mockTransacao = new Transacao();
        mockTransacao.setValor(new BigDecimal("300"));
        mockTransacao.setNumeroConta("123456");

        List<Taxa> listaTaxas = new ArrayList<>();

        when(clienteRepository.findClienteByNumeroConta(any())).thenReturn(Optional.of(mockCliente));
        when(clienteRepository.save(any())).thenReturn(mockCliente);
        when(taxaRepository.findAllByOrderByValorInicialDesc()).thenReturn(listaTaxas);

        Cliente clienteRetorno = transacoesService.sacarValor(mockTransacao);

        Assertions.assertEquals(new BigDecimal("100"), clienteRetorno.getSaldo());
    }

    private void mockarListaDeTaxas() {
        List<Taxa> listaTaxas = new ArrayList<>();
        listaTaxas.add(new Taxa(1, 0.1f, new BigDecimal("300")));
        listaTaxas.add(new Taxa(1, 0.04f, new BigDecimal("100")));

        when(taxaRepository.findAllByOrderByValorInicialDesc()).thenReturn(listaTaxas);
    }
}
