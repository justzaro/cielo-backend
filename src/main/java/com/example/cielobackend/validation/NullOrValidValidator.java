package com.example.cielobackend.validation;

import jakarta.validation.*;

public class NullOrValidValidator implements ConstraintValidator<NullOrValid, Object> {
    private Validator validator;

    @Override
    public void initialize(NullOrValid constraintAnnotation) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return validator.validate(value).isEmpty();
    }
}
