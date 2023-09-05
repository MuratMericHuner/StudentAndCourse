package com.example.demo.Service;


import com.example.demo.Models.Course;
import com.example.demo.Models.Student;

import java.util.List;
import java.util.Map;

public interface CourseService {
    public List<Course> getCourses();

    public void addCourse(Course c);

    public void deleteCourse(Long id);

    public void updateCourse(Long id, Course c);

    public List<String> getStudentWhoTakesTheCourse(Long id);

    public void addStudentToCourse(Long courseId, Long studentId);

    public void deleteStudentFromCourse(Long courseId, Long studentId);
}
