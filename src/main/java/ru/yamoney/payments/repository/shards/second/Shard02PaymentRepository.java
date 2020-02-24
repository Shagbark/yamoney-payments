package ru.yamoney.payments.repository.shards.second;

import org.springframework.stereotype.Repository;
import ru.yamoney.payments.repository.PaymentRepository;

/**
 * The payment repository for the second shard.
 *
 * @author Protsko Dmitry
 */
@Repository
public interface Shard02PaymentRepository extends PaymentRepository {
}
