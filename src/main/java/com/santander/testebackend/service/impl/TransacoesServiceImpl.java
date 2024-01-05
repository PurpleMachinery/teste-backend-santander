package com.santander.testebackend.service.impl;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.model.HistoricoTransacao;
import com.santander.testebackend.model.Transacao;
import com.santander.testebackend.model.enums.TipoTransacao;
import com.santander.testebackend.repository.ClienteRepository;
import com.santander.testebackend.repository.HistoricoTransacaoRepository;
import com.santander.testebackend.service.ITransacoesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacoesServiceImpl implements ITransacoesService {

    private final ClienteRepository clienteRepository;
    private final HistoricoTransacaoRepository historicoTransacaoRepository;

    public TransacoesServiceImpl(ClienteRepository clienteRepository, HistoricoTransacaoRepository historicoTransacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.historicoTransacaoRepository = historicoTransacaoRepository;
    }

    @Override
    public Cliente depositarValor(Transacao transacao) {
        Cliente cliente = clienteRepository.findClienteByNumeroConta(transacao.getNumeroConta()).get();

        cliente.setSaldo(cliente.getSaldo().add(transacao.getValor()));

        HistoricoTransacao historicoTransacao = new HistoricoTransacao.HistoricoTransacaoBuilder(cliente, transacao, TipoTransacao.DEPOSITO).build();
        historicoTransacaoRepository.save(historicoTransacao);

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente sacarValor(Transacao transacao) throws Exception {
        Cliente cliente = clienteRepository.findClienteByNumeroConta(transacao.getNumeroConta()).get();

        BigDecimal valorSubtraido = calcularValorSubtraido(transacao, cliente);
        BigDecimal novoSaldo = cliente.getSaldo().subtract(valorSubtraido);
        
        if(novoSaldo.compareTo(BigDecimal.ZERO) >= 0) {
            cliente.setSaldo(novoSaldo);


            HistoricoTransacao historicoTransacao = new HistoricoTransacao.HistoricoTransacaoBuilder(cliente, transacao, TipoTransacao.SAQUE).build();
            historicoTransacaoRepository.save(historicoTransacao);

            return clienteRepository.save(cliente);
        }
        else {
            throw new Exception("Saldo insuficiente para transação, valor a ser recolhido R$" + valorSubtraido.toString());
        }
    }

    @Override
    public List<HistoricoTransacao> listarTransacoes() {
        return historicoTransacaoRepository.findAll();
    }

    private static BigDecimal calcularValorSubtraido(Transacao transacao, Cliente cliente) {
        BigDecimal taxaAdministrativa = BigDecimal.ZERO;

        if(!cliente.isPlanoExclusive()) {
            if (transacao.getValor().compareTo(new BigDecimal(300)) > 0)
                taxaAdministrativa = transacao.getValor().multiply(new BigDecimal("0.01"));
            else if (transacao.getValor().compareTo(new BigDecimal(100)) > 0)
                taxaAdministrativa = transacao.getValor().multiply(new BigDecimal("0.004"));
        }

        return transacao.getValor().add(taxaAdministrativa);
    }
}
