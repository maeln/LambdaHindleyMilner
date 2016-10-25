package inference;

import inference.interfaces.Substitutable;
import types.TVariable;
import types.Type;

import java.util.*;

/**
 * Created by valentin on 18/10/2016.
 */
public class Substitution implements Substitutable<Substitution>{
    private HashMap<TVariable, Type> subs = new HashMap<>();

    public Substitution() {}

    public Substitution(TVariable variable, Type type) {
        subs.put(variable, type);
    }

    private Substitution(Substitution previous) {
        subs.putAll(previous.subs);
    }

    public Substitution(List<TVariable> vars, List<Type> substitute) {
        if(vars.size() != substitute.size())
            throw new IllegalArgumentException("Number of variables have to match number of types");

        for (int i = 0; i < vars.size(); i++) subs.put(vars.get(i), substitute.get(i));
    }

    public List<TVariable> variables() {
        return new LinkedList<>(subs.keySet());
    }

    public Type substituteOf(TVariable var) {
        return subs.get(var);
    }

    public Substitution compose(Substitution sub) {
        Substitution substituted = new Substitution(sub);
        substituted.apply(this);
        subs.putAll(substituted.subs);
        return this;
    }

    @Override
    public HashSet<TVariable> ftv() {
        HashSet<TVariable> freeVars = new HashSet<>();
        for (TVariable var : variables()) freeVars.addAll(substituteOf(var).ftv());
        return freeVars;
    }

    @Override
    public Substitution substitute(TVariable var, Type t) {
        for (TVariable variable : variables())
            subs.replace(variable, substituteOf(variable).substitute(var, t));
        return this;
    }

    @Override
    public Substitution identity() {
        return this;
    }
}
