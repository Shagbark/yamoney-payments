package ru.yamoney.payments.repository.shards.first;

import org.springframework.stereotype.Repository;
import ru.yamoney.payments.repository.PaymentRepository;

/**
 * The payment repository for first shard.
 *
 * @author Protsko Dmitry
 */
@Repository
public interface PaymentsShard01Repository extends PaymentRepository {
}
