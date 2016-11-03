package types;

import exceptions.UnificationFailException;
import inference.interfaces.Substitutable;
import inference.Substitution;
import inference.interfaces.Unifyable;

/**
 * Created by valentin on 18/10/2016.
 *
 * Interface générale de type.
 */
public abstract class Type extends Substitutable<Type> implements Unifyable{

    @Override
    public Type identity() {
        return this;
    }

    @Override
    public final Substitution unifyWith(Type type) {
        if(this.equals(type)) return new Substitution();
        if(type instanceof TVariable) return unifyWith((TVariable) type);
        if(type instanceof TFunction) return unifyWith((TFunction) type);
        if(type instanceof TConstructor) return unifyWith((TConstructor) type);
        throw new UnificationFailException(this, type);
    }

    protected Substitution unifyWith(TVariable var) {
        return bind(var, this);
    }

    protected Substitution unifyWith(TFunction fun) {
        throw new UnificationFailException(this, fun);
    }

    protected Substitution unifyWith(TConstructor cons) {
        throw new UnificationFailException(this, cons);
    }

    public abstract Type instantiate(Substitution sub);
}
