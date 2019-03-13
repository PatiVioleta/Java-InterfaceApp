package Validator;

/**
 * Validate if exists an entity with the same Id
 */
public class RepetitionException extends RuntimeException{
    /**
     * Constructor
     * @param err - the error message
     */
    public RepetitionException(String err){super(err);}
}
