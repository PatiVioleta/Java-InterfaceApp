package Repo;

import Domain.HasID;
import Validator.RepetitionException;
import Validator.ValidationException;
import Validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * abstract CRUD operations repository
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> - type of entities saved in repository
 */
public abstract class AbstractCRUDRepository<ID, E extends HasID<ID>> implements CrudRepository<ID, E> {
    protected Map<ID,E> entities;
    Validator<E> validator;

    /**
     *
     * @param v the validator for entities
     */
    public AbstractCRUDRepository(Validator v){
        entities=new HashMap<ID,E>();
        validator=v;
    }

    /**
     * @param id - the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public E findOne(ID id){ return entities.get(id); }

    /**
     *@param entity entity must be not null
     *@return null-if the given entity is saved otherwise returns the entity (id already exists)
     *@throws ValidationException if the entity is not valid
     *@throws IllegalArgumentException if the given entity is null
     */
    @Override
    public E save(E entity) throws ValidationException {
        validator.validate(entity);
        if(findOne(entity.getID())==null){
            entities.put(entity.getID(),entity);
            return entity;
        }
        else{
            throw new RepetitionException("Exista o entitate cu acest ID!\n");
        }
    }

    /**
     *  removes the entity with the specified id
     *@param id id must be not null
     *@return the removed entity or null if there is no entity with the given id
     *@throws IllegalArgumentException if the given id is null.
     */
    @Override
    public E delete(ID id){
        if(findOne(id)!=null)
            return entities.remove(id);
        else
            throw new IllegalArgumentException("Nu exista entitate cu acest ID!\n");
    }

    /**
     *@param entity entity must not be null
     *@return null-if the entity is updated, otherwise  returns the entity-(e.g id does not exist).
     *@throws IllegalArgumentException if the given entity is null.
    //*@throws ValidationException if the entity is not valid.
     */
    @Override
    public E update(E entity){
        validator.validate(entity);
        if (findOne(entity.getID()) == null) {
            throw new RepetitionException("Nu exista aceasta entitate!");
        } else {
            return entities.replace(entity.getID(), entity);
        }
    }

    /**
     *
     * @return all entities
     */
    @Override
    public Iterable<E> findAll(){
        return entities.values();
    }

    /**
     *
     * @return the size of the array
     */
    public int size(){
        return entities.size();
    }
}
