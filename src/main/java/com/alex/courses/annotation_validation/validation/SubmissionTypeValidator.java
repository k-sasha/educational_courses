package com.alex.courses.annotation_validation.validation;

import com.alex.courses.annotation_validation.annotation.ValidSubmissionType;
import com.alex.courses.entity.SubmissionType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SubmissionTypeValidator implements ConstraintValidator<ValidSubmissionType, SubmissionType> {
    @Override
    public void initialize(ValidSubmissionType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SubmissionType value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        }

        for (SubmissionType submissionType: SubmissionType.values()) {
            if(value.equals(submissionType)) return true;
        }

        return false;
    }
}
