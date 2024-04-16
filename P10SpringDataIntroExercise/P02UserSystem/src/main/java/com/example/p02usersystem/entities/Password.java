package com.example.p02usersystem.entities;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Invalid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int minLength() default 6;
    int maxLength() default 50;
    boolean requireLowercase() default true;
    boolean requireUppercase() default true;
    boolean requireDigit() default true;
    boolean requireSpecialSymbol() default true;
}
