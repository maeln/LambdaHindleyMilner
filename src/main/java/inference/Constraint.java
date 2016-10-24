package inference;

import types.Type;

/**
 * Created by valentin on 24/10/2016.
 */
public class Constraint {
    private final Type left, right;

    public Constraint(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    public Type left() {
        return left;
    }

    public Type right() {
        return right;
    }
}
