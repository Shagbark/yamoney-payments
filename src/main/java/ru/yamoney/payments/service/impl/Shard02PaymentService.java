package ru.yamoney.payments.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yamoney.payments.repository.PaymentRepository;
import ru.yamoney.payments.service.PaymentService;

/**
 * Service for managing with payments for the second shard.
 *
 * @author Protsko Dmitry
 */
@Service
public class Shard02PaymentService extends AbstractPaymentService implements PaymentService {

    @Autowired
    public Shard02PaymentService(@Qualifier("shard02PaymentRepository") PaymentRepository paymentRepository,
                                 ModelMapper modelMapper) {
        super(paymentRepository, modelMapper);
    }

    @Override
    @Transactional(readOnly = true, transactionManager = "shard02JpaTransactionManager")
    public double sumPaymentsBySender(String sender) {
        return super.sumPaymentsBySender(sender);
    }

}
