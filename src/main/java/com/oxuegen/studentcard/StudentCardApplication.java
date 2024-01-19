package com.oxuegen.studentcard;

import com.github.javafaker.Faker;
import com.oxuegen.studentcard.model.*;
import com.oxuegen.studentcard.repository.StudentCardIdRepository;
import com.oxuegen.studentcard.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            Integer age = faker.number().numberBetween(17, 55);

            Course course = Course
                    .builder()
                    .department("employee")
                    .name("good course")
                    .enrolments(new ArrayList())
                    .build();

            Course secondCourse = Course
                    .builder()
                    .department("customer")
                    .name("great course")
                    .enrolments(new ArrayList())
                    .build();

            Student student = Student.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .age(age)
                    .books(new ArrayList<>())
                    .enrolments(new ArrayList<>())
                    .build();

            student.addBook(Book
                    .builder()
                    .bookName("Book 1")
                    .createdAt(LocalDateTime.now().minusDays(4))
                    .build());

            student.addBook(Book
                    .builder()
                    .bookName("Book 2")
                    .createdAt(LocalDateTime.now().minusDays(2))
                    .build());

            student.addBook(Book
                    .builder()
                    .bookName("Book 3")
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .build());

            student.addEnrolment(Enrolment
                    .builder()
                    .id(new EnrolmentId(student.getId(), course.getId()))
                    .student(student)
                    .course(course)
                    .createdAt(LocalDateTime.now().minusDays(2))
                    .build());

            student.addEnrolment(Enrolment
                    .builder()
                    .id(new EnrolmentId(student.getId(), secondCourse.getId()))
                    .student(student)
                    .course(secondCourse)
                    .createdAt(LocalDateTime.now().minusDays(4))
                    .build());

            StudentIdCard studentIdCard = StudentIdCard
                    .builder()
                    .cardNumber("123456")
                    .student(student)
                    .build();

            student.setStudentIdCard(studentIdCard);
            studentRepository.save(student);

            studentRepository.findById(1L)
                    .ifPresent(s -> {
                        System.out.println("Lazy fetch");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(s.getFirstName() + "got" + book.getBookName());
                        });
                    });
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
