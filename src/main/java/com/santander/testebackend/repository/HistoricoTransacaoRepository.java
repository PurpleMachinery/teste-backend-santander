package com.santander.testebackend.repository;

import com.santander.testebackend.model.HistoricoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoricoTransacaoRepository extends JpaRepository<HistoricoTransacao, Integer> {
    public List<HistoricoTransacao> findAllByDataMovimentacao(LocalDate dataMovimentacao);

}
