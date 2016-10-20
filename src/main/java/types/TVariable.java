package types;

import inference.Substitution;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by valentin on 18/10/2016.
 *
 */
public class TVariable extends Type {
    private final String name;

    public TVariable(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public static TVariable variable(String name) {
        return new TVariable(name);
    }

    public static List<TVariable> variables(String... names) {
        List<TVariable> variables = new LinkedList<>();
        for (String name : names) variables.add(new TVariable(name));
        return variables;
    }

    @Override
    public Type apply(Substitution s) {
        return this.equals(s.variable()) ? s.type() : this;
    }

    @Override
    public TreeSet<TVariable> ftv() {
        return new TreeSet<>(Collections.singleton(this));
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
