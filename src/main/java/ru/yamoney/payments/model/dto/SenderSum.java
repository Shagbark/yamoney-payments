package ru.yamoney.payments.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * This class is DTO (data transfer object) for returning result of calculation
 * total sum of payments by sender.
 *
 * @author Protsko Dmitry
 */
@Value
@Builder
public class SenderSum {

    private String accountFrom;
    private double sum;

}
