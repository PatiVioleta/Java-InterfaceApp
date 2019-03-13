package Validator;

import Domain.Nota;

/**
 * The validator for Nota
 */
public class NotaValidator implements Validator<Nota> {
    /**
     * Validate an entity Nota
     * @param t - the entity Nota to be validated
     * @throws ValidationException if the entity isn't valid
     */
    public void validate(Nota t){
        String err=new String();
        if(t.getID().getKey()==null || t.getID().getKey()<0)
            err=err+"ID student invalid!\n";
        if(t.getID().getValue()==null || t.getID().getValue()<0)
            err=err+"ID tema invalid!\n";
        if(t.getNota()<1 || t.getNota()>10)
            err=err+"Nota invalida!\n";
        if(t.getCadruDidactic().equals(""))
            err=err+"Cadru didactic invalid!\n";
        if(err.length()!=0)
            throw new ValidationException(err);
    }
}
