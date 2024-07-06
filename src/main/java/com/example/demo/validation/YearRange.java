package com.example.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = YearRangeValidator.class)
public @interface YearRange {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int startYear();
    int endYear();
}
