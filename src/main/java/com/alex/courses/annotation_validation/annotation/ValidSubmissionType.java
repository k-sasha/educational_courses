package com.alex.courses.annotation_validation.annotation;

import com.alex.courses.annotation_validation.validation.SubmissionTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SubmissionTypeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSubmissionType {
    String message() default "Invalid value provided. Please use either 'STUDENT_SUBMISSION' or 'CURATOR_FEEDBACK'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

