package com.example.modelmapper_json_xml.util;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyValidator {
    private final Validator validator;

    public MyValidator() {
        validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }

    public <E> List<String> validate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<E> violation : violations) {
            errors.add(violation.getMessage());
        }
        return errors;
    }
}
