package ru.yamoney.payments.service.base;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yamoney.payments.service.PaymentService;

/**
 * This class is a container for all shard's PaymentServices. Using this class it is determined which
 * service is needed for further work with payments.
 *
 * @author Protsko Dmitry
 */
@Component
public class PaymentServiceContainer {

    private final PaymentService shard01PaymentService;
    private final PaymentService shard02PaymentService;
    private final PaymentService shard03PaymentService;

    public PaymentServiceContainer(@Qualifier("shard01PaymentService") PaymentService shard01PaymentService,
                                   @Qualifier("shard02PaymentService") PaymentService shard02PaymentService,
                                   @Qualifier("shard03PaymentService") PaymentService shard03PaymentService) {
        this.shard01PaymentService = shard01PaymentService;
        this.shard02PaymentService = shard02PaymentService;
        this.shard03PaymentService = shard03PaymentService;
    }

    /**
     * Get specialized service by identifier
     *
     * @param numberOfShard shard number
     * @return PaymentService, which configured for certain shard
     */
    PaymentService getService(int numberOfShard) {
        if (numberOfShard == 1) {
            return shard02PaymentService;
        } else if (numberOfShard == 2) {
            return shard03PaymentService;
        } else {
            return shard01PaymentService;
        }
    }

}
