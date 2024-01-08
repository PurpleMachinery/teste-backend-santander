package com.santander.testebackend.config.exception;

public class SaldoInvalidoException extends Exception {

    public SaldoInvalidoException(String errorMessage) {
        super(errorMessage);
    }
}
