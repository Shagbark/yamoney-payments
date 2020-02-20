package ru.yamoney.payments.model.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Protsko Dmitry
 */
@Data
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "account_from")
    private String accountFrom;

    @Column(name = "account_to")
    private String accountTo;

    private double amount;

}
