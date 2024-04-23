package com.example.modelmapper_json_xml.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int minLength;
    private int maxLength;
    private boolean requireLowercase;
    private boolean requireUppercase;
    private boolean requireDigit;
    private boolean requireSpecialSymbol;

    @Override
    public void initialize(Password constraint) {
        this.minLength = constraint.minLength();
        this.maxLength = constraint.maxLength();
        this.requireLowercase = constraint.requireLowercase();
        this.requireUppercase = constraint.requireUppercase();
        this.requireDigit = constraint.requireDigit();
        this.requireSpecialSymbol = constraint.requireSpecialSymbol();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }

        if (requireLowercase && !containsLowercase(password)) {
            return false;
        }

        if (requireUppercase && !containsUppercase(password)) {
            return false;
        }

        if (requireDigit && !containsDigit(password)) {
            return false;
        }

        if (requireSpecialSymbol && !containsSpecialSymbol(password)) {
            return false;
        }

        return true;
    }

    private boolean containsLowercase(String password) {
        return password.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsUppercase(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }

    private boolean containsSpecialSymbol(String password) {
        return password.chars().anyMatch(ch -> "!@#$%^&*()_+<>?".indexOf(ch) >= 0);
    }
}