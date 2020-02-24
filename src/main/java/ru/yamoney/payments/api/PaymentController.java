package ru.yamoney.payments.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yamoney.payments.model.dto.Payment;
import ru.yamoney.payments.model.dto.SenderSum;
import ru.yamoney.payments.service.PaymentService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

/**
 * API for managing payments.
 *
 * @author Protsko Dmitry
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1.0/payment")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Uploads payments into database.
     *
     * @param payments collection of payments
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadPayments(@RequestBody @NotEmpty(message = "Must be at least one payment") Collection<@Valid Payment> payments) {
        paymentService.uploadPayments(payments);
    }

    /**
     * Calculates sum of payments by sender
     *
     * @param accountFrom sender
     * @return sum of payments by sender
     */
    @GetMapping(value = "/{accountFrom}/sum", produces = "application/json")
    public SenderSum getSumOfPaymentsBySender(@PathVariable String accountFrom) {
        double sum = paymentService.sumPaymentsBySender(accountFrom);
        return SenderSum.builder()
                .accountFrom(accountFrom)
                .sum(sum)
                .build();
    }

}
