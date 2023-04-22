package com.alex.courses.service;
import com.alex.courses.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCorses();
    Course saveCourse(Course course);
    Course getCourse(Long id);
    void deleteCourse(Long id);

}
