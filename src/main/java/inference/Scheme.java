package inference;

import types.TVariable;
import types.Type;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by valentin on 18/10/2016.
 */
public class Scheme implements Substitutable<Scheme> {
    private final List<TVariable> variables;
    private final Type type;

    public Scheme(List<TVariable> variables, Type type) {
        this.variables = variables;
        this.type = type;
    }

    public List<TVariable> variables() {
        return variables;
    }

    public Type type() {
        return type;
    }

    public static Scheme forall(Type type) {
        return forall(Collections.emptyList(), type);
    }

    public static Scheme forall(TVariable variable, Type type) {
        return forall(Collections.singletonList(variable), type);
    }

    public static Scheme forall(List<TVariable> variables, Type type) {
        return new Scheme(variables, type);
    }

    // Substitutable - Begin
    @Override
    public Scheme apply(Substitution s) {
        return variables.contains(s.variable()) ? this : forall(variables, type.apply(s));
    }

    @Override
    public Scheme apply(Substitution... substitutions) {
        Scheme result = this;
        for (Substitution s : substitutions) result = result.apply(s);
        return result;
    }

    @Override
    public HashSet<TVariable> ftv() {
        HashSet<TVariable> freeVariables = type.ftv();
        variables.forEach(freeVariables::remove);
        return freeVariables;
    }
    // Substitutable - End

    // Object override - Begin
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scheme scheme = (Scheme) o;

        return variables.equals(scheme.variables) && type.equals(scheme.type);
    }

    @Override
    public int hashCode() {
        int result = variables.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String res = "∀";
        if(!variables.isEmpty()){
            res += variables.get(0).name();
            for (int i = 1; i < variables.size(); i++) res += variables.get(i).name();
        }

        return res + "." + type;
    }

    // Object override - End
}
