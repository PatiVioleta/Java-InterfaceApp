package Repo;

import Domain.HasID;
import Validator.RepetitionException;
import Validator.ValidationException;
import Validator.Validator;

/**
 * abstract CRUD operations repository
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> - type of entities saved in repository
 */
public abstract class AbstractFileRepository<ID,E extends HasID<ID>> extends AbstractCRUDRepository<ID,E> {
    protected String file;

    protected abstract void loadFromFile();
    protected abstract void storeToFile();

    /**
     * constructor
     * @param v the validator for entities
     * @param file the file to be read
     */
    public AbstractFileRepository(Validator v, String file){
        super(v);
        this.file=file;
        loadFromFile();
    }

    /**
     * save an entity in the file
     * @param entity entity must be not null
     * @return the saved entity
     * @throws ValidationException if the entity is not valid
     */
    @Override
    public E save(E entity) throws ValidationException {
        validator.validate(entity);
        if(findOne(entity.getID())==null){
            entities.put(entity.getID(),entity);
            storeToFile();
            return entity;
        }
        else{
            throw new RepetitionException("Exista o entitate cu acest ID!\n");
        }
    }

    /**
     * delete an entity with a specified id
     * @param id id must be not null
     * @return the deleted entity
     */
    @Override
    public E delete(ID id){
        E entity;
        if(findOne(id)!=null)
            entity= entities.remove(id);
        else
            throw new IllegalArgumentException("Nu exista entitate cu acest ID!\n");
        storeToFile();
        return entity;
    }

    /**
     * update an entity with a specified id
     * @param entity entity must not be null
     * @return the updated entity
     */
    @Override
    public E update(E entity){
        validator.validate(entity);
        if (findOne(entity.getID()) == null) {
            throw new RepetitionException("Nu exista aceasta entitate!");
        } else {
            E e = entities.replace(entity.getID(), entity);
            storeToFile();
            return e;
        }
    }

}
