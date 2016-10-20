package inference;

import types.TVariable;

import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 */
public interface Substitutable<T extends Substitutable> {
    T apply(Substitution substitution);
    T apply(Substitution... substitutions);
    HashSet<TVariable> ftv();
}
