package Domain;

/**
 *
 * @param <ID> - entities must have an attribute of type ID
 */
public interface HasID<ID> {
    /**
     *
     * @return the ID of the entity
     */
    ID getID();

    /**
     * Set the id for the entity
     * @param id- the id of the entity to be returned id must not be null
     */
    void setID(ID id);
}
