package com.alex.courses.entity;

import java.util.Arrays;

public enum SubmissionType {
    STUDENT_SUBMISSION,
    CURATOR_FEEDBACK;

    public static String getAvailableValues() {
        return Arrays.toString(SubmissionType.values());
    }
}
