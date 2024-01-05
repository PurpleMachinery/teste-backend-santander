package com.santander.testebackend.repository;

import com.santander.testebackend.model.HistoricoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoTransacaoRepository extends JpaRepository<HistoricoTransacao, Integer> {
}
