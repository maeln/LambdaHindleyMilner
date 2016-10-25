package exceptions;

import inference.environements.UnifyEnv;
import types.Type;

/**
 * Created by valentin on 25/10/2016.
 */
public class UnificationFailException extends RuntimeException {
    public UnificationFailException(Type first, Type second) {
        super("The unification of inferred type for this expression has failed : " + first + " <=/=> " + second);
    }
}
