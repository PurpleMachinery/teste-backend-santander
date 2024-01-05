package com.santander.testebackend.repository;

import com.santander.testebackend.model.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxaRepository extends JpaRepository<Taxa, Integer> {
    public List<Taxa> findAllByOrderByValorInicialDesc();
}
