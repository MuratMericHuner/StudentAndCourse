package com.example.demo.Service;

import com.example.demo.Models.Course;
import com.example.demo.Models.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getStudents();
    public void addNewStudent(Student s);
    public void deleteStudent(Long id);
    public void updateStudent(Long id,Student s);

    public void addCoursetoStudent(Long courseId, Long studentId);
    public void deleteCourseFromStudent(Long courseId, Long studentId);
    public List<String> getStudentsAllCourses(Long id);
}
