package ru.yamoney.payments.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * JSR-303 annotation to validate, that several fields are not equal each other.
 *
 * @author Protsko Dmitry
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotEqualValidator.class)
@Documented
public @interface NotEqual {

    /**
     * Array of fields, which must be not equal each other.
     *
     * @return array of fields
     */
    String[] fields();

    String message() default "Fields must be different.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
