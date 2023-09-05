package com.example.demo.Service;

import com.example.demo.Models.Course;
import com.example.demo.Repo.CourseRepository;
import com.example.demo.Repo.StudentRepository;
import com.example.demo.Models.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService{
    private final StudentRepository studentRepository;
    private  final CourseRepository courseRepository;
    @Override
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    @Override
    public void addNewStudent(Student s){
        Optional<Student> studentByMail = studentRepository.findByMail(s.getMail());
        if(studentByMail.isPresent()){
            throw new IllegalStateException("Email already exist");
        }
        studentRepository.save(s);
    }
    @Override
    public void deleteStudent(Long id){
        boolean isExist = studentRepository.existsById(id);
        if(!isExist){
            throw new IllegalStateException("This student doesn't exist");
        }
        studentRepository.deleteById(id);
    }
    @Override
    @Transactional
    public void updateStudent(Long id,Student s){
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalStateException("Student doesn't exist"));

        if(s.getName()!=null && s.getName().length()>0 && !Objects.equals(student.getName(),s.getName())){
            student.setName(s.getName());
        }

        if(s.getMail()!=null && s.getMail().length()>0 && !Objects.equals(student.getMail(),s.getMail())){
            student.setMail(s.getMail());
        }

        if(s.getDob()!=null && !Objects.equals(student.getDob(),s.getDob())){
            student.setDob(s.getDob());
        }
    }

    @Override
    @Transactional
    public void addCoursetoStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalStateException("No such course"));
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalStateException("No such student"));
        int mandatoryCourses = student.getCourses().stream().filter(c -> c.getCredit()==6).toList().size();
        int optionalCourses = student.getCourses().stream().filter(c -> c.getCredit()==3).toList().size();
        if(!student.getCourses().contains(course)){
            if((course.getCredit()==6 && mandatoryCourses<3) || (course.getCredit()==3 && optionalCourses<4) ){
                student.addCourse(course);
                course.addStudent(student);
            }else if(mandatoryCourses==3){
                throw new IllegalStateException("You cannot add anymore Mandatory courses");
            }else if(optionalCourses==4){
                throw new IllegalStateException("You cannot add anymore Optional courses");
            }
        }else{
            throw new IllegalStateException("Student already takes this course");
        }
    }

    @Override
    @Transactional
    public void deleteCourseFromStudent(Long courseId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalStateException("No such student"));
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalStateException("No such course"));
        student.deleteCourse(course);
        course.deleteStudent(student);
    }

    @Override
    public List<String> getStudentsAllCourses(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalStateException("No such student"));
        return student.getCourses().stream().map(Course::getTitle).toList();
    }
}
