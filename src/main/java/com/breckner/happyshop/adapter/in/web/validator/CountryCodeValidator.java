package com.breckner.happyshop.adapter.in.web.validator;

import com.breckner.happyshop.domain.model.Country;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

    @Override
    public void initialize(ValidCountryCode validProductPath) {
    }

    @Override
    public boolean isValid(String tipoArchivoPath, ConstraintValidatorContext context) {
        return Optional.ofNullable(tipoArchivoPath)
            .map(Country::byCode)
            .isPresent();
    }
}
