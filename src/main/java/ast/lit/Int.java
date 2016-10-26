package ast.lit;

import ast.Lit;

import static types.TConstructor.constructor;

/**
 * Created by valentin on 25/10/2016.
 */
public class Int extends Lit<Integer> {

    public Int(Integer value) {
        super(value, constructor("Int"));
    }
}
