package com.example.demo.Controller;

import com.example.demo.Models.Course;
import com.example.demo.Models.Response;
import com.example.demo.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<Response> getCourses(){
        Collection<Course> courses = courseService.getCourses();
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.FOUND).StatusCode(HttpStatus.FOUND.value())
                .message("All courses retrieved")
                .data(Map.of("Courses",courses))
                .build());
    }
    @GetMapping("/title")
    public ResponseEntity<Response> getTitle(){
        List<Course> credits =courseService.getCourses();
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.FOUND).StatusCode(HttpStatus.FOUND.value())
                .message("All courses retrieved")
                .data(Map.of("Courses",credits.stream().map(Course::getTitle)))
                .build());
    }

    @PostMapping("/save")
    public ResponseEntity<Response> createCourse(@RequestBody Course c){
        if(c.getCredit()==6 || c.getCredit()==3){
            courseService.addCourse(c);
            return ResponseEntity.ok(Response.builder()
                    .timestamps(LocalDateTime.now())
                    .status(HttpStatus.CREATED).StatusCode(HttpStatus.CREATED.value())
                    .message("Course created")
                    .data(Map.of("This course has been created",c.getTitle()))
                    .build());
        }
        return ResponseEntity.badRequest().body(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).StatusCode(HttpStatus.BAD_REQUEST.value())
                .message("Bad Credit")
                .data(Map.of("This course hasn't been created", c.getTitle()))
                .build());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateCourse(@PathVariable("id") Long id, @RequestBody Course c){
        if(c.getCredit()==6 || c.getCredit()==3){
            courseService.updateCourse(id,c);
            return ResponseEntity.ok(Response.builder()
                    .timestamps(LocalDateTime.now())
                    .status(HttpStatus.OK).StatusCode(HttpStatus.OK.value())
                    .message("Course has been updated")
                    .data(Map.of("This course has been updated",c.getTitle()))
                    .build());
        }
        return ResponseEntity.badRequest().body(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).StatusCode(HttpStatus.BAD_REQUEST.value())
                .message("Bad Credit")
                .data(Map.of("This course hasn't been created", c.getTitle()))
                .build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteCourse(@PathVariable("id") Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.OK).StatusCode(HttpStatus.OK.value())
                .message("Course has been deleted")
                .data(Map.of("This course has been deleted",true))
                .build());
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<Response> getStudents(@PathVariable("id") Long id){
        List<String> students = courseService.getStudentWhoTakesTheCourse(id);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.FOUND).StatusCode(HttpStatus.FOUND.value())
                .message("Students who takes this course has been found")
                .data(Map.of("students",students))
                .build());
    }
    @PostMapping("/save/student/{courseId}/{studentId}")
    public ResponseEntity<Response> addStudentToCourse(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId){
        courseService.addStudentToCourse(courseId,studentId);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.OK).StatusCode(HttpStatus.OK.value())
                .message("Students has been added to the course")
                .data(Map.of("students",courseService.getStudentWhoTakesTheCourse(courseId)))
                .build());
    }
    @DeleteMapping("/delete/student/{id}/{studentId}")
    public ResponseEntity<Response> deleteStudentFRomCourse(@PathVariable("id") Long courseId, @PathVariable("studentId") Long studentId){
        courseService.deleteStudentFromCourse(courseId,studentId);
        return ResponseEntity.ok(Response.builder()
                .timestamps(LocalDateTime.now())
                .status(HttpStatus.OK).StatusCode(HttpStatus.OK.value())
                .message("Students has been deleted from course")
                .data(Map.of("students",courseService.getStudentWhoTakesTheCourse(courseId)))
                .build());
    }
}
