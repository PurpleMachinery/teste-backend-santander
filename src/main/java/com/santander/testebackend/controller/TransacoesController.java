package com.santander.testebackend.controller;

import com.santander.testebackend.config.model.ApiErrorResponse;
import com.santander.testebackend.model.Cliente;
import com.santander.testebackend.model.HistoricoTransacao;
import com.santander.testebackend.model.Transacao;
import com.santander.testebackend.service.ITransacoesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/transacoes")
public class TransacoesController {

    private final ITransacoesService transacoesService;

    public TransacoesController(ITransacoesService transacoesService) {
        this.transacoesService = transacoesService;
    }

    @Operation(
            summary = "Sacar valor da conta do cliente",
            description = "Recebe objeto de transação e o utiliza para alterar o valor de saldo do cliente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Transacao.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = "application/json") })
    })
    @PutMapping(value = "/sacar")
    public ResponseEntity<Cliente> sacarValor(@Valid @RequestBody Transacao transacao) throws Exception {
        return ResponseEntity.ok(transacoesService.sacarValor(transacao));
    }

    @Operation(
            summary = "Deposita valor na conta do cliente",
            description = "Recebe objeto de transação e o utiliza para alterar o valor de saldo do cliente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(
                    schema = @Schema(implementation = Transacao.class),
                    mediaType = "application/json") }),
    })
    @PutMapping(value = "/depositar")
    public ResponseEntity<Cliente> depositarValor(@Valid @RequestBody Transacao transacao) {
        return ResponseEntity.ok(transacoesService.depositarValor(transacao));
    }

    @Operation(
            summary = "Lista historico de transações por data",
            description = "Recebe data e o utiliza para listar o histórico de transações durante o dia."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(
                    array = @ArraySchema(schema = @Schema(implementation = HistoricoTransacao.class)),
                    mediaType = "application/json") }),
    })
    @GetMapping(value = "/historico")
    public ResponseEntity<List<HistoricoTransacao>> consultarTransacaoPorData(@RequestParam LocalDate dataMovimentacao) {
        return ResponseEntity.ok(transacoesService.listarTransacoes(dataMovimentacao));
    }
}
