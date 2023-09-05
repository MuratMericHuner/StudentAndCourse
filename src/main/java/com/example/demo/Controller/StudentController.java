package com.example.demo.Controller;

import com.example.demo.Models.Response;
import com.example.demo.Models.Student;
import com.example.demo.Service.StudentServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImp studentservice;
    @GetMapping
    public ResponseEntity<Response> getStudents(){
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .message("Students retrieved")
                .status(HttpStatus.FOUND)
                .StatusCode(HttpStatus.FOUND.value())
                .data(Map.of("students",studentservice.getStudents()))
                .build());
    }
    @PostMapping("/save")
    public ResponseEntity<Response> newStudent(@RequestBody Student s){
        studentservice.addNewStudent(s);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .message("Student added")
                .status(HttpStatus.CREATED)
                .StatusCode(HttpStatus.CREATED.value())
                .data(Map.of("Added",s.getName()))
                .build());
    }
    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Response>  deleteStudent(@PathVariable("studentId") Long id){
        studentservice.deleteStudent(id);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .message("Student deleted")
                .status(HttpStatus.OK)
                .StatusCode(HttpStatus.OK.value())
                .data(Map.of("deleted", true))
                .build());
    }
    @PutMapping("/update/{studentId}")
    public ResponseEntity<Response>  updateStudent(@PathVariable("studentId")Long id, @RequestBody Student s){
        studentservice.updateStudent(id,s);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .message("Student updated")
                .status(HttpStatus.OK)
                .StatusCode(HttpStatus.OK.value())
                .data(Map.of("Student updated",s.getName()))
                .build());
    }
    @GetMapping("/courses/{id}")
    public ResponseEntity<Response> getCoursesFromStudent(@PathVariable("id") Long studentId){
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .message("Courses from student retrieved")
                .status(HttpStatus.FOUND)
                .StatusCode(HttpStatus.FOUND.value())
                .data(Map.of("Courses from student",studentservice.getStudentsAllCourses(studentId)))
                .build());
    }
    @PostMapping("/courses/add/{studentId}/{courseId}")
    public ResponseEntity<Response> addCourseToStudent(@PathVariable("courseId") Long courseId,@PathVariable("studentId") Long studentId){
        studentservice.addCoursetoStudent(courseId,studentId);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .message("Course added to student")
                .status(HttpStatus.OK)
                .StatusCode(HttpStatus.OK.value())
                .data(Map.of("Added",true))
                .build());
    }
    @DeleteMapping("/courses/delete/{studentId}/{courseId}")
    public ResponseEntity<Response> deleteCourseFromStudent(@PathVariable("courseId")Long courseId,@PathVariable("studentId") Long studentId){
        studentservice.deleteCourseFromStudent(courseId,studentId);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .message("Course deleted from student")
                .status(HttpStatus.OK)
                .StatusCode(HttpStatus.OK.value())
                .data(Map.of("Deleted", true))
                .build());
    }
}
