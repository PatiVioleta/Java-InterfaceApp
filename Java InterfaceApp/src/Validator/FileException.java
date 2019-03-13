package Validator;

/**
 * Validate if exists an entity with the same Id
 */
public class FileException extends RuntimeException{
    /**
     * Constructor
     * @param err - the error message
     */
    public FileException(String err){super(err);}
}
