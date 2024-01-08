package com.santander.testebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float porcentagem;
    private BigDecimal valorInicial;
}
