package com.davidagood.aoc2020.day4;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeightValidator implements ConstraintValidator<Height, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (value.endsWith("in")) {
                String inchesValueAsString = value.substring(0, value.length() - 2);
                int inchesValue = Integer.parseInt(inchesValueAsString);
                return inchesValue >= 59 && inchesValue <= 76;
            } else if (value.endsWith("cm")) {
                String centimetersValueAsString = value.substring(0, value.length() - 2);
                int centimetersValue = Integer.parseInt(centimetersValueAsString);
                return centimetersValue >= 150 && centimetersValue <= 193;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}