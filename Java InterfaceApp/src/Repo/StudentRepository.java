package Repo;

import Domain.Student;
import Validator.Validator;

/**
 * CRUD operations repository for Student
 */
public class StudentRepository extends AbstractCRUDRepository<Integer, Student>{

    /**
     *
     * @param v the validator for Student
     */
    public StudentRepository(Validator<Student> v){super(v);}
}
