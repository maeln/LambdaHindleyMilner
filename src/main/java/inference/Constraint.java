package inference;

import inference.interfaces.Substitutable;
import types.TVariable;
import types.Type;

import java.util.HashSet;

/**
 * Created by valentin on 24/10/2016.
 */
public class Constraint implements Substitutable<Constraint> {
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

    @Override
    public HashSet<TVariable> ftv() {
        HashSet<TVariable> freeVars = new HashSet<>();
        freeVars.addAll(left.ftv());
        freeVars.addAll(right.ftv());
        return freeVars;
    }

    @Override
    public Constraint substitute(TVariable var, Type t) {
        return new Constraint(left.substitute(var, t), right.substitute(var, t));
    }

    @Override
    public Constraint identity() {
        return this;
    }

    public Substitution unify() {
        return left.unifyWith(right);
    }
}
