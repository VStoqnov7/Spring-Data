package com.example.util;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class MyValidator {
    private final Validator localValidator;

    public MyValidator() {
        this.localValidator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    public boolean isValid(Object object) {
        Set<ConstraintViolation<Object>> errors =
                this.localValidator.validate(object);

        return errors.isEmpty();
    }
}