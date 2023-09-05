package com.example.demo.Config;

import com.example.demo.Models.Course;
import com.example.demo.Models.Student;
import com.example.demo.Repo.CourseRepository;
import com.example.demo.Repo.StudentRepository;
import com.example.demo.Service.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class DBConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, CourseService courseService){
        return args -> {
            Student murat = new Student("Murat","mertle1995@gmail.com",LocalDate.of(1995, Month.MARCH,19));
            Student jeremy =  new Student("Jeremy","jeremy@gmail.com", LocalDate.of(1995, Month.JANUARY,26));
            Course AA = new Course("Architecture des Applications",6);
            Course TDL =  new Course("Theorie des Langages", 6);
            Course Bash =  new Course("Bash",3);
            courseService.addCourse(AA);
            courseService.addCourse(TDL);
            courseService.addCourse(Bash);
            studentRepository.saveAll(List.of(murat,jeremy));
        };
    }
}
