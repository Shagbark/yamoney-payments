package ru.yamoney.payments.service;

import ru.yamoney.payments.model.dto.Payment;

import java.util.Collection;

/**
 * The main interface for managing payments.
 *
 * @author Protsko Dmitry
 */
public interface PaymentService {

    /**
     * Upload a collection of payments into database.
     *
     * @param payments collection of payments
     */
    void uploadPayments(Collection<Payment> payments);

    /**
     * Calculate sum of payments of passed sender account.
     *
     * @param sender the sender (accountFrom), whose payment amount is to be calculated
     * @return calculated sum of payments amount
     * @throws ru.yamoney.payments.exception.AccountNotFoundException if account was not found
     */
    double sumPaymentsBySender(String sender);

}
