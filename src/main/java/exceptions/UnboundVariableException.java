package exceptions;

import ast.Variable;

/**
 * Created by valentin on 20/10/2016.
 */
public class UnboundVariableException extends RuntimeException {
    public UnboundVariableException(Variable var) {
        super("The variable '" + var.getName() + "' is not bound.");
    }
}
