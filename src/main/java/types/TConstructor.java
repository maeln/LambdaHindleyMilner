package types;

import inference.Substitution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by valentin on 18/10/2016.
 *
 * Constructeur de type. Ceci n'est pas utilisé pour le moment.
 */
public class  TConstructor extends Type {
    private final static Map<String, TConstructor> instantiated = new HashMap<>();
    private final String name;

    private TConstructor(String name) {
        System.out.println("Creation of TConstructor(" + name + ")");
        this.name = name;
    }

    public static TConstructor constructor(String name) {
        TConstructor cons = instantiated.get(name);
        if(cons == null) {
            cons = new TConstructor(name);
            instantiated.put(name, cons);
        }
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

    @Override
    public Type instantiate(Substitution sub) {
        return this;
    }
    // Object override - End
}
