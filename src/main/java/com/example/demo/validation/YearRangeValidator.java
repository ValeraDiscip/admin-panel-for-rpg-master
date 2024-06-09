package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;

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
        return date != null && date.getYear() >= startYear && date.getYear() <= endYear;
    }
}