package com.santander.testebackend.controller;

import com.santander.testebackend.config.model.ApiErrorResponse;
import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
            summary = "Cadastrar cliente",
            description = "Ao enviar objeto válido para o endpoint um usuário será criado e assim retornará o mesmo como objeto de resposta."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Cliente.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = "application/json") })
    })
    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.salvarCliente(cliente));
    }

    @Operation(
            summary = "Lista todos clientes",
            description = "Listará todos clientes como objeto de resposta."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Cliente.class), mediaType = "application/json") })
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }
}
