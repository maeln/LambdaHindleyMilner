package types;

import inference.Substitution;

import java.util.*;

/**
 * Created by valentin on 18/10/2016.
 *
 */
public class TVariable extends Type {
    private final String name;
    private final static HashSet<TVariable> instantiated = new HashSet<>();

    private TVariable(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public static TVariable variable(String name) {
        TVariable var = new TVariable(name);
        if(!instantiated.add(var)) System.out.println("Creation avoidable : TVariable(" + name + ")");
        return var;
    }

    public static List<TVariable> variables(String... names) {
        List<TVariable> variables = new LinkedList<>();
        for (String name : names) variables.add(new TVariable(name));
        return variables;
    }

    @Override
    public Type substitute(TVariable var, Type type) {
        return this.equals(var) ? type : this;
    }

    @Override
    public HashSet<TVariable> ftv() {
        return new HashSet<>(Collections.singleton(this));
    }

    @Override
    protected Substitution unifyWith(TFunction fun) {
        return bind(this, fun);
    }

    @Override
    protected Substitution unifyWith(TConstructor cons) {
        return bind(this, cons);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TVariable tVariable = (TVariable) o;

        return name.equals(tVariable.name);

    }

    @Override
    public int hashCode() {
        return ("TVariable_" + name).hashCode();
    }

    @Override
    public String toString() {
        return name();
    }
}
