package Repo;

import Domain.Tema;
import Validator.Validator;

/**
 * CRUD operations repository for Tema
 */
public class TemaRepository extends AbstractCRUDRepository<Integer, Tema> {

    /**
     *
     * @param v the validator for Tema
     */
    public TemaRepository(Validator<Tema> v){super(v);}
}
