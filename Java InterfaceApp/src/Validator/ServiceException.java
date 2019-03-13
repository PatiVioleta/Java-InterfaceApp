package Validator;

/**
 * The exception class for service
 */
public class ServiceException extends RuntimeException {
    /**
     * Constructor
     * @param err - the message error
     */
    public ServiceException(String err){
        super(err);
    }
}
