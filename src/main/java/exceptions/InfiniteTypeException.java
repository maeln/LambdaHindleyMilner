package exceptions;

import inference.interfaces.Unifyable;
import types.Type;

/**
 * Created by valentin on 25/10/2016.
 */
public class InfiniteTypeException extends RuntimeException {
    public InfiniteTypeException(Unifyable t1, Unifyable t2) {
        super("This expression require an infinite type. (Trying to unify " + t1 + " and " +t2);
    }
}
