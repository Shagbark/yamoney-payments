package ru.yamoney.payments.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * This class is a special format of errors.
 * This class contains information about error, which occurred during process request.
 *
 * @author Protsko Dmitry
 */
@Value
@Builder
public class ApplicationError {

    /**
     * Error code
     * @see ru.yamoney.payments.exception.ErrorCodes
     */
    private int errorCode;
    /**
     * Error message
     */
    private String errorMessage;

}
