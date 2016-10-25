package types;

import inference.interfaces.Substitutable;
import inference.Substitution;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 *
 * Interface générale de type.
 */
public abstract class Type implements Substitutable<Type> {

    @Override
    public Type apply(Substitution substitution) {
        Type result = this;
        for (TVariable var : substitution.variables()) result = result.substitute(var, substitution.substituteOf(var));
        return result;
    }

    @Override
    public Type identity() {
        return this;
    }
}
