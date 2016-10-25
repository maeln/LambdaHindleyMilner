package inference.interfaces;

import inference.Substitution;
import types.TVariable;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 */
public interface Substitutable<T extends Substitutable> {
    T apply(Substitution substitution);
    T apply(Substitution... substitutions);
    T apply(Collection<Substitution> substitutions);
    HashSet<TVariable> ftv();
}
