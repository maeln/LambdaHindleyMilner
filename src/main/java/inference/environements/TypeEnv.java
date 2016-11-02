package inference.environements;

import ast.Variable;
import exceptions.UnboundVariableException;
import types.Scheme;
import inference.Substitution;
import inference.interfaces.Substitutable;
import types.TVariable;
import types.Type;

import java.util.*;

/**
 * Created by valentin on 20/10/2016.
 *
 */
public class TypeEnv extends Substitutable<TypeEnv> {
    private final HashMap<Variable, Scheme> env;

    public TypeEnv() {
        this.env = new HashMap<>();
    }

    /**
     * Create a new fresh TypeEnv with a single binding.
     * @param var The variable to bind.
     * @param scheme The scheme to bind.
     * @return A fresh new TypeEnv with one binding (var => scheme)
     */
    public static TypeEnv singleton(Variable var, Scheme scheme) {
        TypeEnv typeEnv = new TypeEnv();
        typeEnv.extend(var, scheme);
        return typeEnv;
    }

    /**
     * Extends the given TypeEnv with a new binding
     * @param var the variable to bind.
     * @param scheme the Scheme to bind.
     */
    public void extend(Variable var, Scheme scheme) {
        env.put(var, scheme);
    }

    /**
     * Extends the current TypeEnv with the given list of variables and schemes.
     * @param vars The list of variable to bind.
     * @param schemes The list of schemes to bind.
     * @throws IllegalArgumentException If the length of the two given lists are not the same.
     */
    public void extend(List<Variable> vars, List<Scheme> schemes) throws IllegalArgumentException {
        if(vars.size() != schemes.size())
            throw new IllegalArgumentException("Same number of variables and schemes required to extend the TypeEnv");

        for (int i = 0; i < vars.size(); i++) {
           env.put(vars.get(i), schemes.get(i));
        }
    }

    /**
     * Get the Scheme bound to the given variable in the current TypeEnv.
     * @param var the variable to match against.
     * @return the Scheme bound to the given variable.
     */
    public Scheme lookup(Variable var) {
        Scheme scheme = env.get(var);
        if(scheme == null) throw new UnboundVariableException(var);
        else return scheme;
    }

    /**
     * Check if a variable is already bound in the current TypeEnv
     * @param var the variable to check against
     * @return True if the variable is bound. (False will be returned if the variable is bound to a null Scheme).
     */
    public boolean contains(Variable var) {
        return env.containsKey(var) && env.get(var) != null;
    }

    /**
     * Get the variables bound in the current TypeEnv
     * @return a list of defined variables (null Schemes will be ignored)
     */
    public List<Variable> variables() {
        return new ArrayList<>(env.keySet());
    }

    /**
     * Merge the current TypeEnv with the given one.
     * If a variable is present in both TypeEnv, the Scheme of the current TypeEnv will be preferred.
     * @param typeEnv2 The TypeEnv to merge with.
     */
    public TypeEnv mergeWith(TypeEnv typeEnv2) {
        HashMap<Variable, Scheme> typeEnv2Unified = new HashMap<>();

        //Make a fresh copy of typeEnv2 and delete all duplicate variables. This is to have a left-biased merge.
        typeEnv2Unified.putAll(typeEnv2.env);
        variables().forEach(typeEnv2Unified::remove);

        env.putAll(typeEnv2Unified);
        return this;
    }

    /**
     * Remove a binding form the current TypeEnv
     * @param var The variabe to unbind.
     */
    public void remove(Variable var) {
        env.remove(var);
    }

    /**
     * Check if the current TypeEnv have at least one binding.
     * @return True if at least one binding is present.
     */
    public boolean isEmpty() {
        return env.isEmpty();
    }

    // Substitutable - Begin
    // WARNING : The implementation of Substitutable<T> is here mutable to match with
    // the behavior of the other methods of the class... TODO: Unify the behavior of this functions.
    @Override
    public TypeEnv apply(Substitution substitution) {
        for (Variable var : variables()) env.replace(var, env.get(var).apply(substitution));
        return this;
    }

    @Override
    public HashSet<TVariable> ftv() {
        HashSet<TVariable> set = new HashSet<>();
        for (Variable var : variables()) set.addAll(env.get(var).ftv());
        return set;
    }

    @Override
    public TypeEnv substitute(TVariable var, Type t) {
        for(Variable variable : variables()) env.replace(variable, env.get(variable).substitute(var, t));
        return this;
    }

    @Override
    public TypeEnv identity() {
        return this;
    }
    // Substitutable - End
}
