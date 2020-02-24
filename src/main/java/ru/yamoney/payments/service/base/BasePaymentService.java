package ru.yamoney.payments.service.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yamoney.payments.model.dto.Payment;
import ru.yamoney.payments.service.PaymentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("paymentService")
@RequiredArgsConstructor
public class BasePaymentService implements PaymentService {

    private final PaymentServiceContainer delegator;

    @Override
    @Transactional(transactionManager = "chainedTransactionManager")
    public void uploadPayments(Collection<Payment> payments) {
        Map<Integer, List<Payment>> paymentsGroupedByShardNumber = payments.stream()
                .collect(Collectors.groupingBy(payment -> defineShardNumber(payment.getAccountFrom())));

        paymentsGroupedByShardNumber.forEach(
                (shardNumber, listOfPayments) -> delegator.getService(shardNumber).uploadPayments(listOfPayments)
        );
    }

    @Override
    public double sumPaymentsBySender(String sender) {
        return delegator.getService(defineShardNumber(sender)).sumPaymentsBySender(sender);
    }

    private int defineShardNumber(String accountFrom) {
        return Math.abs(accountFrom.hashCode() % 3);
    }

}
