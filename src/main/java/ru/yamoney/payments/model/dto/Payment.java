package ru.yamoney.payments.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * This class is DTO (data transfer object), which represents entity
 * {@link ru.yamoney.payments.model.domain.PaymentEntity}.
 *
 * @author Protsko Dmitry
 */
@Value
@Builder
public class Payment {

    /**
     * Sender value
     */
    private String accountFrom;
    /**
     * Recipient value
     */
    private String accountTo;
    /**
     * Amount of payment
     */
    private double amount;

}
