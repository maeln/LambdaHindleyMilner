package types;

import inference.Substitution;

import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 *
 * Constructeur de type. Ceci n'est pas utilisé pour le moment.
 */
public class TConstructor extends Type {
    private final static HashSet<TConstructor> instantiated = new HashSet<>();
    private final String name;

    private TConstructor(String name) {
        System.out.println("Creation of TConstructor(" + name + ")");
        this.name = name;
    }

    public static TConstructor constructor(String name) {
        TConstructor cons = new TConstructor(name);
        if(instantiated.add(cons)) System.out.println("Creation avoidable : TConstructor(" + name + ")");
        return cons;
    }

    public String name() {
        return name;
    }

    @Override
    public Type apply(Substitution substitution) {
        return this;
    }

    @Override
    public Type substitute(TVariable var, Type type) {
        return this;
    }

    @Override
    public HashSet<TVariable> ftv() {
        return new HashSet<>();
    }

    // Object override - Begin
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TConstructor that = (TConstructor) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
    // Object override - End
}
