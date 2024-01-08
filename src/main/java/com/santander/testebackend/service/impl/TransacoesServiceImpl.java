package com.santander.testebackend.service.impl;

import com.santander.testebackend.config.exception.SaldoInvalidoException;
import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.model.HistoricoTransacao;
import com.santander.testebackend.model.Taxa;
import com.santander.testebackend.model.Transacao;
import com.santander.testebackend.model.enums.TipoTransacao;
import com.santander.testebackend.repository.ClienteRepository;
import com.santander.testebackend.repository.HistoricoTransacaoRepository;
import com.santander.testebackend.repository.TaxaRepository;
import com.santander.testebackend.service.ITransacoesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacoesServiceImpl implements ITransacoesService {

    private final ClienteRepository clienteRepository;
    private final TaxaRepository taxaRepository;
    private final HistoricoTransacaoRepository historicoTransacaoRepository;

    public TransacoesServiceImpl(ClienteRepository clienteRepository, TaxaRepository taxaRepository, HistoricoTransacaoRepository historicoTransacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.taxaRepository = taxaRepository;
        this.historicoTransacaoRepository = historicoTransacaoRepository;
    }

    @Override
    public Cliente depositarValor(Transacao transacao) {
        Cliente cliente = clienteRepository.findClienteByNumeroConta(transacao.getNumeroConta()).get();

        cliente.setSaldo(cliente.getSaldo().add(transacao.getValor()));


        salvarHistoricoTransacao(cliente, transacao, TipoTransacao.DEPOSITO);

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente sacarValor(Transacao transacao) throws Exception {
        Cliente cliente = clienteRepository.findClienteByNumeroConta(transacao.getNumeroConta()).get();

        BigDecimal valorSubtraido = calcularValorSubtraido(transacao, cliente.isPlanoExclusive());
        BigDecimal novoSaldo = cliente.getSaldo().subtract(valorSubtraido);
        
        if(novoSaldo.compareTo(BigDecimal.ZERO) >= 0) {
            cliente.setSaldo(novoSaldo);

            salvarHistoricoTransacao(cliente, transacao, TipoTransacao.SAQUE);

            return clienteRepository.save(cliente);
        }
        else {
            throw new SaldoInvalidoException("Saldo insuficiente para transação, valor a ser recolhido R$" + valorSubtraido.toString());
        }
    }

    @Override
    public List<HistoricoTransacao> listarTransacoes(LocalDate dataMovimentacao) {
        return historicoTransacaoRepository.findAllByDataMovimentacao(dataMovimentacao);
    }

    private void salvarHistoricoTransacao(Cliente cliente, Transacao transacao, TipoTransacao tipoTransacao) {
        HistoricoTransacao historicoTransacao = new HistoricoTransacao.HistoricoTransacaoBuilder(cliente, transacao, TipoTransacao.SAQUE).build();
        historicoTransacaoRepository.save(historicoTransacao);
    }

    private BigDecimal calcularValorSubtraido(Transacao transacao, boolean clienteExclusive) {
        BigDecimal taxaAdministrativa = BigDecimal.ZERO;

        List<Taxa> listaTaxas = taxaRepository.findAllByOrderByValorInicialDesc();

        if(!clienteExclusive) {
            for (Taxa taxa : listaTaxas) {
                if (transacao.getValor().compareTo(taxa.getValorInicial()) > 0) {
                    float porcentagemConcreta = 1 + taxa.getPorcentagem();
                    return transacao.getValor().multiply(BigDecimal.valueOf(porcentagemConcreta));
                }
            }
        }

        return transacao.getValor().add(taxaAdministrativa);
    }
}
