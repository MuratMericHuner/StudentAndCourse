package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private long courseId;
    private String title;
    private  Integer credit;
    private String type;

    @ManyToMany
    @JoinTable(
            name = "student_course_map",
            joinColumns = @JoinColumn(
                    name="",
                    referencedColumnName = "courseId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="student_id",
                    referencedColumnName = "id"
            )
    )
    private List<Student> students;

    public Course(String title, Integer credit) {
        this.title = title;
        this.credit = credit;
    }
    public void addStudent(Student s){
        this.students.add(s);
    }

    public void deleteStudent(Student s){
        this.students.remove(s);
    }
}
