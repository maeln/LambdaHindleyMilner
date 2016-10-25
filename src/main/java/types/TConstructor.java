package types;

import inference.Substitution;

import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 *
 * Constructeur de type. Ceci n'est pas utilisé pour le moment.
 */
public class TConstructor extends Type {
    //TODO

    private String name;

    public TConstructor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "TConstructor{" +
                "name='" + name + '\'' +
                '}';
    }
    // Object override - End
}
