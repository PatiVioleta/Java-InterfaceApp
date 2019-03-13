package Validator;

import Domain.Tema;

/**
 * the validator for the entity Tema
 */
public class TemaValidator implements Validator<Tema> {
    /**
     * Validate an entity Tema
     * @param t - the entity Tema to be validated
     * @throws ValidationException if the entity isn't valid
     */
    public void validate(Tema t) throws ValidationException {
        String err=new String();
        if(t.getID()==null || t.getID()<0)
            err=err+"ID invalid!\n";
        if(t.getDescriere().equals(""))
            err=err+"Descriere invalida!\n";
        if(t.getDeadline()<1 || t.getDeadline()>14)
            err=err+"Deadline invalid!\n";
        if(t.getPrimire()<1 || t.getPrimire()>14 || t.getPrimire()>t.getDeadline())
            err=err+"Saptamana primire invalida!\n";
        if(err.length()!=0)
            throw new ValidationException(err);
    }
}
