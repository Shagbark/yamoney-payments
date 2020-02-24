package ru.yamoney.payments.service.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.yamoney.payments.model.dto.Payment;
import ru.yamoney.payments.service.PaymentService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BasePaymentServiceTest {

    @Mock
    private PaymentServiceContainer delegator;
    @Mock
    private PaymentService shard01PaymentService;
    @Mock
    private PaymentService shard02PaymentService;
    @Mock
    private PaymentService shard03PaymentService;

    @InjectMocks
    private BasePaymentService service;

    @BeforeEach
    public void setupDelegator() {
        when(delegator.getService(0)).thenReturn(shard01PaymentService);
        when(delegator.getService(1)).thenReturn(shard02PaymentService);
        when(delegator.getService(2)).thenReturn(shard03PaymentService);
    }

    @Test
    @DisplayName("Delegate payments upload to the first shard paymentService")
    public void testUploadPayments_whenAccountFromIdentifierIs0_thenDelegateToFirstShard() {
        List<Payment> payments = Collections.singletonList(
                Payment.builder()
                        .accountFrom("5545-3434-1433-3434")
                        .accountTo("5545-3434-1433-3434")
                        .amount(453)
                        .build()
        );

        service.uploadPayments(payments);
        verify(shard01PaymentService).uploadPayments(payments);
        verify(shard02PaymentService, times(0)).uploadPayments(payments);
        verify(shard03PaymentService, times(0)).uploadPayments(payments);
    }

    @Test
    @DisplayName("Delegate payments upload to the second shard paymentService")
    public void testUploadPayments_whenAccountFromIdentifierIs1_thenDelegateToSecondShard() {
        List<Payment> payments = Collections.singletonList(
                Payment.builder()
                        .accountFrom("6543-3434-1341-3434")
                        .accountTo("5545-3434-1433-3434")
                        .amount(453)
                        .build()
        );

        service.uploadPayments(payments);
        verify(shard02PaymentService).uploadPayments(payments);
        verify(shard01PaymentService, times(0)).uploadPayments(payments);
        verify(shard03PaymentService, times(0)).uploadPayments(payments);
    }

    @Test
    @DisplayName("Delegate payments upload to the third shard paymentService")
    public void testUploadPayments_whenAccountFromIdentifierIs2_thenDelegateToThirdShard() {
        List<Payment> payments = Collections.singletonList(
                Payment.builder()
                        .accountFrom("34234-3245-64344-3244")
                        .accountTo("5545-3434-1433-3434")
                        .amount(453)
                        .build()
        );

        service.uploadPayments(payments);
        verify(shard03PaymentService).uploadPayments(payments);
        verify(shard01PaymentService, times(0)).uploadPayments(payments);
        verify(shard02PaymentService, times(0)).uploadPayments(payments);
    }

    @Test
    @DisplayName("Delegate calculation of payments amounts by sender to the first shard paymentService")
    public void testSumPaymentsBySender_whenAccountFromIdentifierIs0_thenDelegateToFirstShard() {
        String sender = "5545-3434-1433-3434";

        service.sumPaymentsBySender(sender);
        verify(shard01PaymentService).sumPaymentsBySender(sender);
        verify(shard02PaymentService, times(0)).sumPaymentsBySender(sender);
        verify(shard03PaymentService, times(0)).sumPaymentsBySender(sender);
    }

    @Test
    @DisplayName("Delegate calculation of payments amounts by sender to the second shard paymentService")
    public void testSumPaymentsBySender_whenAccountFromIdentifierIs1_thenDelegateToSecondShard() {
        String sender = "6543-3434-1341-3434";

        service.sumPaymentsBySender(sender);
        verify(shard02PaymentService).sumPaymentsBySender(sender);
        verify(shard01PaymentService, times(0)).sumPaymentsBySender(sender);
        verify(shard03PaymentService, times(0)).sumPaymentsBySender(sender);
    }

    @Test
    @DisplayName("Delegate calculation of payments amounts by sender to the third shard paymentService")
    public void testSumPaymentsBySender_whenAccountFromIdentifierIs2_thenDelegateToThirdShard() {
        String sender = "34234-3245-64344-3244";

        service.sumPaymentsBySender(sender);
        verify(shard03PaymentService).sumPaymentsBySender(sender);
        verify(shard01PaymentService, times(0)).sumPaymentsBySender(sender);
        verify(shard02PaymentService, times(0)).sumPaymentsBySender(sender);
    }

}