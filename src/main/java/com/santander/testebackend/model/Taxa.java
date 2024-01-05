package com.santander.testebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float porcentagem;
    private BigDecimal valorInicial;
}
