package Validator;

/**
 * The validator
 * @param <E> - a type of objects to be validated
 */
public interface Validator<E> {
    /**
     * validate an entity of type E
     * @param entity - the entity to be validated
     * @throws ValidationException if the entity isn't valid
     */
    void validate(E entity) throws ValidationException;
}
