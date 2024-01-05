package com.santander.testebackend.controller;

import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.model.HistoricoTransacao;
import com.santander.testebackend.model.Transacao;
import com.santander.testebackend.service.ITransacoesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/transacoes")
public class TransacoesController {

    private final ITransacoesService transacoesService;

    public TransacoesController(ITransacoesService transacoesService) {
        this.transacoesService = transacoesService;
    }

    @PutMapping(value = "/sacar")
    public ResponseEntity<Cliente> sacarValor(@Valid @RequestBody Transacao transacao) throws Exception {
        return ResponseEntity.ok(transacoesService.sacarValor(transacao));
    }

    @PutMapping(value = "/depositar")
    public ResponseEntity<Cliente> depositarValor(@Valid @RequestBody Transacao transacao) {
        return ResponseEntity.ok(transacoesService.depositarValor(transacao));
    }

    @GetMapping(value = "/historico")
    public ResponseEntity<List<HistoricoTransacao>> consultarTransacaoPorData() {
        return ResponseEntity.ok(transacoesService.listarTransacoes());
    }
}
