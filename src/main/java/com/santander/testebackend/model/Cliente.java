package com.santander.testebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data @Entity @NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 6, max = 6)
    @NotBlank(message = "\"numeroConta\" é obrigatório.")
    @Column(unique = true)
    private String numeroConta;

    @NotBlank(message = "\"nome\" é obrigatório.")
    private String nome;

    @PositiveOrZero(message = "\"saldo\" precisa ser positivo ou igual a zero.")
    private BigDecimal saldo;

    @NotNull
    private LocalDate dataNascimento;

    private boolean planoExclusive;
}
