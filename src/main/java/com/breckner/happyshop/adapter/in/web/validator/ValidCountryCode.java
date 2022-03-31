package com.breckner.happyshop.adapter.in.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryCodeValidator.class)
public @interface ValidCountryCode {

    String message() default "tipoArchivo path no es válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
