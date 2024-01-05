package com.santander.testebackend.service;

import com.santander.testebackend.model.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> listarClientes();

    Cliente salvarCliente(Cliente cliente);
}
