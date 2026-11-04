package com.flow.api.exception;

public class BankTransactionNotFoundException extends RuntimeException {
    public BankTransactionNotFoundException(Long id) {
        super("Bank transaction with id " + id + " not found");
    }
}
