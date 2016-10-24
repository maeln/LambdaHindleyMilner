package types;

import inference.Infer;
import inference.Substitutable;
import inference.Substitution;

import java.util.Collection;

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

    @Override
    public Type apply(Collection<Substitution> substitutions) {
        Type result = this;
        for (Substitution sub : substitutions) result = this.apply(sub);
        return result;
    }
}
