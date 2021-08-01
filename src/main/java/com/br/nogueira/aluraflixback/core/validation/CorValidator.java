package com.br.nogueira.aluraflixback.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.*;

public class CorValidator implements ConstraintValidator<Cor, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^#[0-9A-F]{6}$");
    }
}
