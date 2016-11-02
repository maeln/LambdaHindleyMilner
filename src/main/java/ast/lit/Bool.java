package ast.lit;

import ast.Literal;

import static types.TConstructor.constructor;

/**
 * Created by valentin on 25/10/2016.
 */
public class Bool extends Literal<Boolean> {

    public Bool(Boolean value) {
        super(value, constructor("Bool"));
    }
}
