package ru.yamoney.payments.exception;

/**
 * Exception must be thrown when account was not found in shards.
 *
 * @author Protsko Dmitry
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }

}
