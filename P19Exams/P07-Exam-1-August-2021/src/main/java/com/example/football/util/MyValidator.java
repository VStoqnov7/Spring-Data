package com.example.football.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyValidator {

    private Validator validator;

    public MyValidator() {
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    public <E> boolean isValid(E entity){
        return this.validator.validate(entity).isEmpty();
    }

    public <E> List<String> validate(E entity){
        Set<ConstraintViolation<E>> validate = validator.validate(entity);
        List<String> errors = new ArrayList<>();
        validate.forEach(validation-> errors.add(validation.getMessage()));
        return errors;
    }
}
