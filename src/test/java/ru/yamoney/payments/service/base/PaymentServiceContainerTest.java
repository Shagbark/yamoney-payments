package ru.yamoney.payments.service.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yamoney.payments.service.PaymentService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceContainerTest {

    private static PaymentService shard01PaymentService = Mockito.mock(PaymentService.class);
    private static PaymentService shard02PaymentService = Mockito.mock(PaymentService.class);
    private static PaymentService shard03PaymentService = Mockito.mock(PaymentService.class);

    private PaymentServiceContainer container;

    @BeforeEach
    public void setupContainer() {
        container = new PaymentServiceContainer(shard01PaymentService, shard02PaymentService, shard03PaymentService);
    }

    @ParameterizedTest
    @ArgumentsSource(PaymentServiceArgumentProvider.class)
    public void testGetService_whenInputParameterIsIdentifier_thenReturnAssociatedPaymentServiceInstance
            (int identifier, PaymentService expectedService) {
        PaymentService service = container.getService(identifier);
        assertSame(expectedService, service);
    }

    /**
     * Arguments provider for parametrized test. Provides pair of identifier and paymentService, which is related
     * to this identifier.
     */
    public static class PaymentServiceArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(0, shard01PaymentService),
                    Arguments.of(1, shard02PaymentService),
                    Arguments.of(2, shard03PaymentService)
            );
        }
    }

}