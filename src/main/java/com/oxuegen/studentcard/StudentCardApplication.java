package com.oxuegen.studentcard;

import com.oxuegen.studentcard.model.Student;
import com.oxuegen.studentcard.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class StudentCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentCardApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student philip = Student
                    .builder()
                    .firstName("Philip")
                    .lastName("Dubrovskiy")
                    .email("p.dubrovskiy@mail.ru")
                    .age(22)
                    .build();
            Student maria = Student
                    .builder()
                    .firstName("Maria")
                    .lastName("Dubovick")
                    .email("m.dubovick@mail.ru")
                    .age(25)
                    .build();

            studentRepository.saveAll(List.of(philip, maria));

            studentRepository
                    .findByEmail("p.dubrovskiy@mail.ru")
                    .ifPresentOrElse(System.out::println, () -> System.out.println("email not found"));

            studentRepository.findStudentsByFirstNameEqualsAndAgeAfter(
                    "Philip",
                    20
            ).forEach(System.out::println);
        };
    }

}
