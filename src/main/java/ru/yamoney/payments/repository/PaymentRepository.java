package ru.yamoney.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.yamoney.payments.model.domain.PaymentEntity;

/**
 * Base repository for accessing to payment table
 *
 * @author Protsko Dmitry
 */
@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
