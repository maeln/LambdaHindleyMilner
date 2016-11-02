package types;

import inference.Substitution;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by valentin on 18/10/2016.
 */
public class TFunction extends Type {
    private  Type left;
    private  Type right;
    private final static HashMap<Integer, TFunction> instantiated = new HashMap<>();

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
        int hash = hash(left, right);
        TFunction f = instantiated.get(hash);
        if(f == null) {
            f = new TFunction(left, right);
            instantiated.put(hash, f);
        }
        return f;
    }

    private static int hash(Type left, Type right) {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }

    // Substitutable - Begin
    @Override
    public Type substitute(TVariable var, Type type) {
        TFunction result;
        String before = this.toString();
        //*
        left = left.substitute(var, type);
        right = right.substitute(var, type);
        result = this;
        /*/
        result = function(left.substitute(var, type), right.substitute(var, type));
        //*/

        System.out.println(var + " => " + type + " \n\t" + before + " => " + result + "\n");
        return result;
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
        Substitution sub =  sub2.composeWith(sub1);
        System.out.println("Unifyed " + this + " with " + fun + " : ");
        System.out.println(sub);
        return sub;
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
        return hash(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + " → " + right + ")";
    }

    //Object override - End
}
