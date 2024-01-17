package com.oxuegen.studentcard.repository;

import com.oxuegen.studentcard.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where s.email = ?1")
    Optional<Student> findByEmail(String email);
    @Query("select s from Student s WHERE s.firstName = ?1 and s.age >= ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeAfter(String firstName, Integer age);

    @Query(
            value = "select * from student WHERE first_name = :firstName AND age >= :age",
            nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeAfterNative(
            @Param("firstName") String firstName,
            @Param("age") Integer age);
}
