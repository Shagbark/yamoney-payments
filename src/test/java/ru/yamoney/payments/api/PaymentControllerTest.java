package ru.yamoney.payments.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.yamoney.payments.exception.AccountNotFoundException;
import ru.yamoney.payments.exception.ErrorCodes;
import ru.yamoney.payments.model.dto.Payment;
import ru.yamoney.payments.service.PaymentService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    private static final String UPLOAD_PAYMENTS_URL = "/api/1.0/payment";
    private static final String GET_SUM_OF_PAYMENTS_BY_SENDER_URL = "/api/1.0/payment/%s/sum";

    @Autowired
    private MockMvc mockMvc;
    @MockBean(name = "paymentService")
    private PaymentService paymentService;

    @Test
    @DisplayName("Send response with error BAD_INPUT_PARAMETERS and status 400, when empty array was in request")
    public void testUploadPayments_whenEmptyArrayOfPayments_thenHandleExceptionAndReturn400WithSpecialFormat() throws Exception {
        mockMvc.perform(
                    post(UPLOAD_PAYMENTS_URL)
                            .content("[]")
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCodes.BAD_INPUT_PARAMETERS));
    }

    @Test
    @DisplayName("Send response with error BAD_INPUT_PARAMETERS and status 400, when invalid payment exists in request")
    public void testUploadPayments_whenPaymentIsInvalid_thenHandleExceptionAndReturn400WithSpecialFormat() throws Exception {
        mockMvc.perform(
                    post(UPLOAD_PAYMENTS_URL)
                            .content("[{}]")
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCodes.BAD_INPUT_PARAMETERS));
    }

    @Test
    @DisplayName("Send response with error BAD_INPUT_PARAMETERS and status 400, when accountFrom and accountTo are equals")
    public void testUploadPayments_whenAccountFromAndToAreEquals_thenHandleExceptionAndReturn400WithSpecialFormat() throws Exception {
        Payment payment = Payment.builder()
                .accountFrom("345-434")
                .accountTo("345-434")
                .amount(4534.43)
                .build();

        String request = new ObjectMapper().writeValueAsString(Collections.singletonList(payment));
        mockMvc.perform(
                    post(UPLOAD_PAYMENTS_URL)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCodes.BAD_INPUT_PARAMETERS));
    }

    @Test
    @DisplayName("Send response with error APPLICATION_ERROR and status 500, when unexpected error occurred during request process")
    public void testUploadPayments_whenUnexpectedErrorOccurredDuringRequestProcess_thenHandleExceptionAndReturn500() throws Exception {
        Payment payment = Payment.builder()
                .accountFrom("345-434")
                .accountTo("344-554")
                .amount(4534.43)
                .build();

        String request = new ObjectMapper().writeValueAsString(Collections.singletonList(payment));

        doThrow(RuntimeException.class).when(paymentService).uploadPayments(anyCollection());

        mockMvc.perform(
                    post(UPLOAD_PAYMENTS_URL)
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value(ErrorCodes.APPLICATION_ERROR));
    }

    @Test
    @DisplayName("Return 201 when all payments were successfully uploaded")
    public void testUploadPayments_whenPaymentsIsValid_thenInvokeServiceMethodAndReturn201() throws Exception {
        Payment payment = Payment.builder()
                .accountFrom("345-434")
                .accountTo("344-554")
                .amount(4534.43)
                .build();

        String request = new ObjectMapper().writeValueAsString(Collections.singletonList(payment));

        mockMvc.perform(
                post(UPLOAD_PAYMENTS_URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Return sum of payments by sender")
    public void testGetSumOfPaymentsBySender_whenAccountExist_thenReturnSumOfPayments() throws Exception {
        String accountFrom = "4564-3453";
        double sum = 563.45;

        when(paymentService.sumPaymentsBySender(accountFrom)).thenReturn(sum);

        mockMvc.perform(
                    get(String.format(GET_SUM_OF_PAYMENTS_BY_SENDER_URL, accountFrom))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountFrom").value(accountFrom))
                .andExpect(jsonPath("$.sum").value(sum));
    }

    @Test
    @DisplayName("Return response with error code ACCOUNT_NOT_FOUND, when sender in request doesn't exist")
    public void testGetSumOfPaymentsBySender_whenAccountNotExist_thenHandleExceptionAndReturn400() throws Exception {
        String accountFrom = "4564-3453";

        when(paymentService.sumPaymentsBySender(accountFrom)).thenThrow(AccountNotFoundException.class);

        mockMvc.perform(
                        get(String.format(GET_SUM_OF_PAYMENTS_BY_SENDER_URL, accountFrom))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(ErrorCodes.ACCOUNT_NOT_FOUND));
    }

}