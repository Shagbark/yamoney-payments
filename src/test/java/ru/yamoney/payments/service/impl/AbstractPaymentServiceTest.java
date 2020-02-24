package ru.yamoney.payments.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import ru.yamoney.payments.exception.AccountNotFoundException;
import ru.yamoney.payments.model.domain.PaymentEntity;
import ru.yamoney.payments.model.dto.Payment;
import ru.yamoney.payments.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AbstractPaymentServiceTest {

    /**
     * This class is an empty implementation of {@link AbstractPaymentService} needed for
     * testings.
     */
    static class DefaultPaymentService extends AbstractPaymentService {
        public DefaultPaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper) {
            super(paymentRepository, modelMapper);
        }
    }

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DefaultPaymentService service;

    @Test
    @DisplayName("Map payments dtos to entities and call repository #save method")
    public void testUploadPayments_whenPaymentsIsValid_whenMapPaymentsToEntityAndSaveToDatabase() {
        Collection<Payment> payments = new ArrayList<>(Arrays.asList(
            Payment.builder().accountFrom("4534-3454").accountTo("4534-6777").amount(454.34).build(),
            Payment.builder().accountFrom("5564-4354").accountTo("5554-4234").amount(656.4).build(),
            Payment.builder().accountFrom("5454-2344").accountTo("7575-4234").amount(19094).build()
        ));

        when(modelMapper.map(any(Payment.class), eq(PaymentEntity.class))).thenReturn(new PaymentEntity());

        service.uploadPayments(payments);
        verify(modelMapper, times(payments.size())).map(any(Payment.class), eq(PaymentEntity.class));
        verify(paymentRepository, times(payments.size())).save(any(PaymentEntity.class));
    }

    @Test
    @DisplayName("Throw AccountNotFoundException when account doesn't exist in database")
    public void testSumPaymentsBySender_whenNoAccountInDatabase_whenThrowException() {
        String sender = "5435-2342-6345-6766";
        when(paymentRepository.existsByAccountFrom(sender)).thenReturn(false);

        assertThrows(AccountNotFoundException.class, () -> service.sumPaymentsBySender(sender));
        verify(paymentRepository, times(0)).sumPaymentsAmountsBySender(sender);
    }

    @Test
    @DisplayName("Get sum of payments amounts by sender")
    public void testSumPaymentsBySender_whenAccountExistInDatabase_whenCalculateSumOfPaymentsOfThisAccount() {
        String sender = "5345-2342-4534-5454";
        double expectedSum = 5365.23;

        when(paymentRepository.existsByAccountFrom(sender)).thenReturn(true);
        when(paymentRepository.sumPaymentsAmountsBySender(sender)).thenReturn(expectedSum);

        double sum = service.sumPaymentsBySender(sender);
        assertEquals(expectedSum, sum);
    }

}