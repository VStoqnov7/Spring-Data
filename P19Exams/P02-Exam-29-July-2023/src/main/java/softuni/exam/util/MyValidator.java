package softuni.exam.util;

import javax.validation.Validation;
import javax.validation.Validator;

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
}
