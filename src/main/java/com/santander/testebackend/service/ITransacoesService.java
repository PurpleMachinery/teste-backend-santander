package com.santander.testebackend.service;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.model.HistoricoTransacao;
import com.santander.testebackend.model.Transacao;

import java.util.List;

public interface ITransacoesService {
    public Cliente depositarValor(Transacao transacao);
    public Cliente sacarValor(Transacao transacao) throws Exception;
    public List<HistoricoTransacao> listarTransacoes();
}
