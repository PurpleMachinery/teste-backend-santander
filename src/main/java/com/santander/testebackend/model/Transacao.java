package com.santander.testebackend.model;

import com.santander.testebackend.model.enums.TipoTransacao;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MappedSuperclass
public class Transacao {
    private TipoTransacao tipoTransacao;

    @PositiveOrZero(message = "\"valor\" precisa ser positivo ou igual a zero.")
    private BigDecimal valor;

    @NotBlank(message = "\"numeroConta\" é obrigatório.")
    private String numeroConta;
}
