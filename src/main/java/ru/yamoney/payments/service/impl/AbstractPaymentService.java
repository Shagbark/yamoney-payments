package ru.yamoney.payments.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.yamoney.payments.exception.AccountNotFoundException;
import ru.yamoney.payments.model.domain.PaymentEntity;
import ru.yamoney.payments.model.dto.Payment;
import ru.yamoney.payments.repository.PaymentRepository;
import ru.yamoney.payments.service.PaymentService;

import java.util.Collection;

/**
 * Service with common operations on payments. It has default implementations of
 * all operations on payments.
 *
 * @author Protsko Dmitry
 */
@RequiredArgsConstructor
public abstract class AbstractPaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "chainedTransactionManager")
    public void uploadPayments(Collection<Payment> payments) {
        payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentEntity.class))
                .forEach(paymentRepository::save);
    }

    @Override
    public double sumPaymentsBySender(String sender) {
        if (!paymentRepository.existsByAccountFrom(sender)) {
            throw new AccountNotFoundException(String.format("Account %s was not fount.", sender));
        }
        return paymentRepository.sumPaymentsAmountsBySender(sender);
    }

}
