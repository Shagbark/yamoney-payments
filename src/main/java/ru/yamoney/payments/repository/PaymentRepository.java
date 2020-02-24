package ru.yamoney.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yamoney.payments.model.domain.PaymentEntity;

/**
 * Base repository for accessing to payment table
 *
 * @author Protsko Dmitry
 */
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    /**
     *
     * @param accountFrom
     * @return
     */
    @Query(
            value = "select sum(amount) from payments p where p.account_from = :accountFrom",
            nativeQuery = true
    )
    double sumPaymentsAmountsBySender(@Param("accountFrom") String accountFrom);

    /**
     * Проверяет, существуют ли хотя бы один платеж, совершенный данным аккаунтом
     *
     * @param accountFrom акк
     * @return true, false
     */
    boolean existsByAccountFrom(String accountFrom);

}
