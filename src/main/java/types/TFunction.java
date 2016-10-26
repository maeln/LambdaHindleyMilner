package types;

import inference.Substitution;

import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 */
public class TFunction extends Type {
    private final Type left;
    private final Type right;
    private final static HashSet<TFunction> instantiated = new HashSet<>();

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
        TFunction f = new TFunction(left, right);
        if(!instantiated.add(f)) System.out.println("Creation avoidable : TFunction(" + left + ", " + right + ")" );
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


    @Override
    protected Substitution unifyWith( TFunction fun) {
        Substitution sub1 = left.unifyWith(fun.left);
        Substitution sub2 = right.apply(sub1).unifyWith(fun.right.apply(sub1));
        return sub2.composeWith(sub1);
    }

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
