package softuni.exam.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationUtilImpl implements ValidationUtil{

    private final Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }

    public <E> List<String> validate(E entity){
        Set<ConstraintViolation<E>> validate = this.validator.validate(entity);
        List<String> errors = new ArrayList<>();

        validate.forEach(validation -> errors.add(validation.getMessage()));
        return errors;
    }
}
