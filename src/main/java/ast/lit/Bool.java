package ast.lit;

import ast.Lit;
import types.TConstructor;

import static types.TConstructor.constructor;

/**
 * Created by valentin on 25/10/2016.
 */
public class Bool extends Lit<Boolean> {

    public Bool(Boolean value) {
        super(value, constructor("Bool"));
    }
}
