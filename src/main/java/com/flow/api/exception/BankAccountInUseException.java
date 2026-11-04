package com.flow.api.exception;

public class BankAccountInUseException extends RuntimeException {
    public BankAccountInUseException(Long id) {

        super("Bank account with id " + id + " still contains transactions");

    }
}
