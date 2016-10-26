package types;

import inference.Substitution;
import inference.interfaces.Substitutable;

import java.util.*;

/**
 * Created by valentin on 18/10/2016.
 */
public class Scheme implements Substitutable<Scheme> {
    private final List<TVariable> variables;
    private final Type type;
    private final static HashSet<Scheme> instantiated = new HashSet<>();

    private Scheme(List<TVariable> variables, Type type) {
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
        Scheme sch = new Scheme(variables, type);
        if(!instantiated.add(sch)) System.out.println("Creation avoidable");
        return sch;
    }

    // Substitutable - Begin

    @Override
    public HashSet<TVariable> ftv() {
        HashSet<TVariable> freeVariables = type.ftv();
        variables.forEach(freeVariables::remove);
        return freeVariables;
    }

    @Override
    public Scheme substitute(TVariable var, Type t) {
        return variables.contains(var) ? this : forall(variables, type.substitute(var, t));
    }

    @Override
    public Scheme identity() {
        return this;
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
