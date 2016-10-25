package exceptions;

/**
 * Created by valentin on 25/10/2016.
 */
public class InfiniteTypeException extends RuntimeException {
    public InfiniteTypeException() {
        super("This expression require an infinite type.");
    }
}
