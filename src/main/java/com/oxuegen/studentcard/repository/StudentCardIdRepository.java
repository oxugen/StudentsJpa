package com.oxuegen.studentcard.repository;

import com.oxuegen.studentcard.model.StudentIdCard;
import org.springframework.data.repository.CrudRepository;

public interface StudentCardIdRepository
        extends CrudRepository<StudentIdCard, Long> {

}
