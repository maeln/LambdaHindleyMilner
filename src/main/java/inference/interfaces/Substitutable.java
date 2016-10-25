package inference.interfaces;

import inference.Substitution;
import types.TVariable;
import types.Type;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 */
public interface Substitutable<T extends Substitutable<T>> {
    default T apply(Substitution s) {
        T result = identity();
        for (TVariable var : s.variables()) {
            result = result.substitute(var, s.substituteOf(var));
        }
        return result;
    }
    HashSet<TVariable> ftv();

    T substitute(TVariable var, Type t);

    T identity();
}
