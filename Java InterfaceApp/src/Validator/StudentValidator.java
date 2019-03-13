package Validator;
import Domain.Student;

/**
 * the validator for Student
 */
public class StudentValidator implements Validator<Student>{
    /**
     * Validate an entity Student
     * @param s - the entity Student to be validated
     * @throws ValidationException if the entity isn't valid
     */
    public void validate(Student s) throws ValidationException{
        String err=new String();
        if(s.getID()==null || s.getID()<0)
            err=err+"ID invalid!\n";
        if(s.getNume().equals(""))
            err=err+"Nume invalid!\n";
        if(s.getGrupa()<221 || s.getGrupa()>227)
            err=err+"Grupa invalida!\n";
        if(s.getEmail().equals(""))
            err=err+"Email invalid!\n";
        if(err.length()!=0)
            throw new ValidationException(err);

    }
}
