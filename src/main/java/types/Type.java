package types;

import inference.Substitutable;
import inference.Substitution;

/**
 * Created by valentin on 18/10/2016.
 *
 * Interface générale de type.
 */
public abstract class Type implements Substitutable<Type> {

    @Override
    public Type apply(Substitution... substitutions) {
        Type result = this;
        for (Substitution s : substitutions) result = result.apply(s);
        return result;
    }
}
