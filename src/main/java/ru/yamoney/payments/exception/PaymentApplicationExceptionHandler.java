package ru.yamoney.payments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yamoney.payments.model.dto.ApplicationError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Application exception handler. Catches exceptions and transform it into special error format.
 *
 * @author Protsko Dmitry
 */
@RestControllerAdvice
public class PaymentApplicationExceptionHandler  {

    /**
     * Handle {@link AccountNotFoundException} and transform response to special format
     * {@link ApplicationError}.
     *
     * @param exc exception {@link AccountNotFoundException}, which will be caught.
     * @return custom error response
     */
    @ExceptionHandler(AccountNotFoundException.class)
    ResponseEntity<ApplicationError> notFoundHandler(AccountNotFoundException exc) {
        return new ResponseEntity<>(
                ApplicationError.builder()
                        .errorCode(ErrorCodes.ACCOUNT_NOT_FOUND)
                        .errorMessage(exc.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handle {@link ConstraintViolationException} and transform response to special format
     * {@link ApplicationError}.
     *
     * @param exc exception {@link ConstraintViolationException}, which will be caught.
     * @return custom error response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> invalidInputParametersHandler(ConstraintViolationException exc) {
        String message = exc.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(" "));

        return new ResponseEntity<>(
                ApplicationError.builder()
                        .errorCode(ErrorCodes.BAD_INPUT_PARAMETERS)
                        .errorMessage(message)
                        .build(),
                HttpStatus.BAD_REQUEST
        );

    }

    /**
     * Handle all other exceptions (which not handled by other methods in current class) and transform
     * response to special format {@link ApplicationError}.
     *
     * @param exc exception, which will be caught.
     * @return custom error response
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApplicationError> internalServerErrorHandler(Exception exc) {
        return new ResponseEntity<>(
                ApplicationError.builder()
                        .errorCode(ErrorCodes.APPLICATION_ERROR)
                        .errorMessage(exc.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
