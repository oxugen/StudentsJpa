package com.oxuegen.studentcard.repository;

import com.oxuegen.studentcard.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where s.email = ?1")
    Optional<Student> findByEmail(String email);
    List<Student> findStudentsByFirstNameEqualsAndAgeAfter(String firstName, Integer age);
}
