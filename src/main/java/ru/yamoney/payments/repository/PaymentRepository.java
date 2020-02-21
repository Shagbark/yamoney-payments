package ru.yamoney.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.yamoney.payments.model.domain.PaymentEntity;

/**
 * Base repository for accessing to payment table
 *
 * @author Protsko Dmitry
 */
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
