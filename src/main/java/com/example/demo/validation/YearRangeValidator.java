package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class YearRangeValidator implements ConstraintValidator<YearRange, Date> {
    private int startYear;
    private int endYear;
    
    @Override
    public void initialize(YearRange constraintAnnotation) {
         this.startYear = constraintAnnotation.startYear();
         this.endYear = constraintAnnotation.endYear();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return date != null
                && startYear - 1900 <= date.getYear() && date.getYear() <= endYear - 1900;
    }
}