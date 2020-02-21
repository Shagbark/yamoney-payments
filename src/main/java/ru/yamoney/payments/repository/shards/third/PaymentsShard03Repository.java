package ru.yamoney.payments.repository.shards.third;

import org.springframework.stereotype.Repository;
import ru.yamoney.payments.repository.PaymentRepository;

/**
 * The payment repository for the third shard.
 *
 * @author Protsko Dmitry
 */
@Repository
public interface PaymentsShard03Repository extends PaymentRepository {
}
