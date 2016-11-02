package inference.interfaces;

import inference.Substitution;
import types.TVariable;
import types.Type;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 */
public abstract class Substitutable<T extends Substitutable<T>> {
    public T apply(Substitution s) {
        T result = identity();
        for (TVariable var : s.variables()) {
            result = result.substitute(var, s.substituteOf(var));
        }
        return result;
    }
    abstract public HashSet<TVariable> ftv();

    abstract public T substitute(TVariable var, Type t);

    abstract public T identity();
}
