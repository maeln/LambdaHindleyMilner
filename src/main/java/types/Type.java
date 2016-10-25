package types;

import exceptions.UnificationFailException;
import inference.Constraint;
import inference.environements.UnifyEnv;
import inference.interfaces.Substitutable;
import inference.Substitution;
import inference.interfaces.Unifyable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by valentin on 18/10/2016.
 *
 * Interface générale de type.
 */
public abstract class Type implements Substitutable<Type>, Unifyable{

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

    @Override
    public final Substitution unifyWith(Type type) {
        if(type instanceof TVariable) return bind((TVariable) type, this);
        if(type instanceof TFunction) return unifyWith((TFunction) type);
        if(type instanceof TConstructor) return unifyWith((TConstructor) type);
        throw new UnificationFailException(this, type);
    }

    protected Substitution unifyWith(TFunction fun) {
        throw new UnificationFailException(this, fun);
    }

    protected Substitution unifyWith(TConstructor cons) {
        throw new UnificationFailException(this, cons);
    }
}
