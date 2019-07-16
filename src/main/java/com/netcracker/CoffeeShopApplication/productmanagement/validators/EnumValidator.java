package com.netcracker.CoffeeShopApplication.productmanagement.validators;

import com.netcracker.CoffeeShopApplication.productmanagement.validators.annotations.Enum;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class EnumValidator implements ConstraintValidator<Enum, String> {
    private Enum annotation;

    @Override
    public void initialize(Enum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
