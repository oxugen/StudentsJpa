package com.oxuegen.studentcard;

import com.github.javafaker.Faker;
import com.oxuegen.studentcard.model.Student;
import com.oxuegen.studentcard.model.StudentIdCard;
import com.oxuegen.studentcard.repository.StudentCardIdRepository;
import com.oxuegen.studentcard.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class StudentCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentCardApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentCardIdRepository studentCardIdRepository){
        return args -> {
            Faker faker = new Faker();
            for(int i = 0;i <= 20; i++){
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = String.format("%s.%s@gmail.com", firstName, lastName);
                Integer age = faker.number().numberBetween(17, 55);
                Student student = Student.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .age(age)
                        .build();

                studentRepository.save(student);
            }
        };
    }

    private static Page<Student> getStudents(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(
                0,
                5,
                Sort.by("firstName").ascending());
        //sorting(studentRepository);
        Page<Student> page = studentRepository.findAll(pageRequest);
        return page;
    }

    private static void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach(System.out::println);
    }

    private void generateRandomStudents(StudentRepository studentRepository){
        Faker faker = new Faker();
        for(int i = 0; i < 20; i++){
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String
                    .format("%s.%s@gmail.com", firstName, lastName)
                    .toLowerCase();
            Integer age = faker.number().numberBetween(17, 55);
            Student student = Student.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .age(age)
                    .build();

            studentRepository.save(student);
        }
    }

}
