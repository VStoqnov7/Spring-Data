package softuni.exam.instagraphlite.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyValidator {

    private final Validator validator;

    public MyValidator() {
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }


    public <E> boolean isValid(E entity){
        return this.validator.validate(entity).isEmpty();
    }



    public <E> List<String> validator(E entity){
        Set<ConstraintViolation<E>> validate = this.validator.validate(entity);
        List<String> errors = new ArrayList<>();
        validate.forEach(validation -> errors.add(validation.getMessage()));
        return errors;
    }
}