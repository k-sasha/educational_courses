package com.alex.courses.annotation_validation.annotation;

import com.alex.courses.annotation_validation.validation.SubmissionTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SubmissionTypeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSubmissionType {
    String message() default "Invalid value provided.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

