package Repo;

import Domain.Nota;
import Validator.Validator;
import javafx.util.Pair;

/**
 * CRUD operations repository for Nota
 */
public class NotaRepository extends AbstractCRUDRepository<Pair<Integer,Integer>, Nota> {

    /**
     *
     * @param v the validator for nota
     */
    public NotaRepository(Validator<Nota> v){super(v);}
}
