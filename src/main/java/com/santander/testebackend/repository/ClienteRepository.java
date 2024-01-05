package com.santander.testebackend.repository;

import com.santander.testebackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    public Optional<Cliente> findClienteByNumeroConta(String numeroConta);
}
