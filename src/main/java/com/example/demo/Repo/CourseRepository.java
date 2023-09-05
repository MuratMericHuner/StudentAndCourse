package com.example.demo.Repo;

import com.example.demo.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    @Query("SELECT c FROM Course c WHERE c.title = ?1")
    Optional<Course> findByName(String name);
}
