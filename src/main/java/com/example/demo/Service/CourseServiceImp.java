package com.example.demo.Service;

import com.example.demo.Models.Course;
import com.example.demo.Models.CourseType;
import com.example.demo.Models.Student;
import com.example.demo.Repo.CourseRepository;
import com.example.demo.Repo.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RequiredArgsConstructor
@Service
public class CourseServiceImp implements CourseService{
    private final CourseRepository courseRepository;
    private  final StudentRepository studentRepository;

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void addCourse(Course c) {
        if(courseRepository.findById(c.getCourseId()).isPresent() || courseRepository.findByName(c.getTitle()).isPresent()) throw new IllegalStateException("Course already exists");
        if(c.getCredit()==6 || c.getCredit()==3){
            if(c.getCredit()==6)c.setType(CourseType.MANDATORY.name());
            if(c.getCredit()==3)c.setType(CourseType.OPTIONAL.name());
            courseRepository.save(c);
        }else{
            throw new IllegalStateException("Course can only have 3 or 6 credit");
        }
    }

    @Override
    public void deleteCourse(Long id) {
        if(courseRepository.findById(id).isEmpty()) throw new IllegalStateException("There is no such course");
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateCourse(Long id, Course c) {
        if(c.getCredit()==6 || c.getCredit()==3){
            Course course = courseRepository.findById(id).orElseThrow(()-> new IllegalStateException("There is  no such course"));
            course.setCredit(c.getCredit());
            course.setTitle(c.getTitle());
            if(course.getCredit()==6)course.setType(CourseType.MANDATORY.name());
            if(course.getCredit()==3)course.setType(CourseType.OPTIONAL.name());
        }else{
            throw new IllegalStateException("Course can only have 3 or 6 credit");
        }
    }

    @Override
    public List<String> getStudentWhoTakesTheCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(()-> new IllegalStateException("There is  no such course"));
        return course.getStudents().stream().map(Student::getName).toList();
    }

    @Override
    @Transactional
    public void addStudentToCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalStateException("There is  no such course"));
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalStateException("There is  no such student"));
        if(!course.getStudents().contains(student)){
            course.addStudent(student);
            student.addCourse(course);
        }else{
            throw new IllegalStateException("Student already takes this course");
        }
    }

    @Override
    @Transactional
    public void deleteStudentFromCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalStateException("There is  no such course"));
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalStateException("There is  no such student"));
        if(!course.getStudents().isEmpty()){
            course.deleteStudent(student);
            student.deleteCourse(course);
        }
    }
}
