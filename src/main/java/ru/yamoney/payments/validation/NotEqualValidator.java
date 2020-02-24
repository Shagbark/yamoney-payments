package ru.yamoney.payments.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Validator to check that several fields are not equal each other
 */
public class NotEqualValidator implements ConstraintValidator<NotEqual, Object> {

   /**
    * Collection of field names, which must be not equal each other
    */
   private Collection<String> fields;

   @Override
   public void initialize(NotEqual constraint) {
      fields = new ArrayList<>(Arrays.asList(constraint.fields()));
   }

   @Override
   public boolean isValid(Object obj, ConstraintValidatorContext context) {
      BeanWrapper beanWrapper = new BeanWrapperImpl(obj);

      long uniqueValuesInFields = fields.stream()
              .map(beanWrapper::getPropertyValue)
              .distinct()
              .count();

      return uniqueValuesInFields == fields.size();
   }

}
