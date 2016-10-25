package types;

import inference.Substitution;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by valentin on 18/10/2016.
 */
public class TFunction extends Type {
    private final Type left;
    private final Type right;

    private TFunction(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    public Type left() {
        return left;
    }

    public Type right() {
        return right;
    }

    public static TFunction function(Type left, Type right) {
        return new TFunction(left, right);
    }

    // Substitutable - Begin
    @Override
    public Type substitute(TVariable var, Type type) {
        return function(left.substitute(var, type), right.substitute(var, type));
    }

    @Override
    public HashSet<TVariable> ftv() {
        HashSet<TVariable> set = new HashSet<>(left.ftv());
        set.addAll(right.ftv());
        return set;
    }
    //Substitutable - End

    // Object override - Begin
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFunction tFunction = (TFunction) o;

        return left.equals(tFunction.left) && right.equals(tFunction.right);

    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "(" + left + " → " + right + ")";
    }

    //Object override - End
}
