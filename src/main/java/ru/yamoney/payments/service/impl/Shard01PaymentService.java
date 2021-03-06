package ru.yamoney.payments.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yamoney.payments.repository.PaymentRepository;
import ru.yamoney.payments.service.PaymentService;

/**
 * Service for managing with payments for the first shard.
 *
 * @author Protsko Dmitry
 */
@Service
public class Shard01PaymentService extends AbstractPaymentService implements PaymentService {

    @Autowired
    public Shard01PaymentService(@Qualifier("shard01PaymentRepository") PaymentRepository paymentRepository,
                                 ModelMapper modelMapper) {
        super(paymentRepository, modelMapper);
    }

    @Override
    @Transactional(readOnly = true, transactionManager = "transactionManager")
    public double sumPaymentsBySender(String sender) {
        return super.sumPaymentsBySender(sender);
    }

}
