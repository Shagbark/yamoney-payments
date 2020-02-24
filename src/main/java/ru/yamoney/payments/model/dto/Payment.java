package ru.yamoney.payments.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yamoney.payments.validation.NotEqual;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

/**
 * This class is DTO (data transfer object), which represents entity
 * {@link ru.yamoney.payments.model.domain.PaymentEntity}.
 *
 * @author Protsko Dmitry
 */
@Data
@Builder
@JsonDeserialize(builder = Payment.PaymentBuilder.class)
@NotEqual(fields = {
        "accountFrom",
        "accountTo"
})
public class Payment {

    /**
     * Sender value
     */
    @NotBlank(message = "The accountFrom must not be empty.")
    @Length(max = 31, message = "AccountFrom length must be less than 30 symbols.")
    private String accountFrom;
    /**
     * Recipient value
     */
    @NotBlank(message = "The accountFrom must not be empty.")
    @Length(max = 31, message = "AccountTo length must be less than 30 symbols.")
    private String accountTo;
    /**
     * Amount of payment
     */
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.01", message = "The amount must be less than 0.01.")
    private double amount;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PaymentBuilder {

    }

}
