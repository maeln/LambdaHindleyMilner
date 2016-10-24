package inference;

import ast.*;
import types.TFunction;
import types.TVariable;
import types.Type;

import java.util.*;

import static inference.Scheme.forall;
import static types.TFunction.function;
import static types.TVariable.variable;

/**
 * Created by valentin on 24/10/2016.
 */
public class Infer {
    private TypeEnv env;
    private TypeGenerator typeGenerator;
    private List<Constraint> constraints;

    public Infer() {
        this.env = new TypeEnv();
        this.typeGenerator = new TypeGenerator();
        this.constraints = new LinkedList<>();
    }

    public Infer(Infer global) {
        this.env = new TypeEnv();
        this.typeGenerator = global.typeGenerator;
        this.constraints = global.constraints;
    }

    private void unify(Type t1, Type t2) {
        constraints.add(new Constraint(t1, t2));
    }

    public Infer inEnv(Variable var, Scheme scheme) {
        Infer local = new Infer(this);
        local.env.extend(var, scheme);
        return local;
    }

    private Type instantiate(Scheme scheme) {
        List<Substitution> substitutions = new LinkedList<>();

        for (TVariable var : scheme.variables()) {
            substitutions.add(new Substitution(var, typeGenerator.freshName()));
        }

        return scheme.type().apply(substitutions);
    }

    public Scheme generalize(Type t) {
        HashSet<TVariable> freeVars = t.ftv();
        freeVars.removeAll(env.ftv());
        return forall(new ArrayList<>(freeVars), t);
    }

    public Type infer(Expression exp) {
        if (exp instanceof Variable) return infer((Variable) exp);
        if (exp instanceof Application) return infer((Application) exp);
        if (exp instanceof Lambda) return infer((Lambda) exp);
        if (exp instanceof Let) return infer((Let) exp);
        else throw new IllegalArgumentException("Unkown Expression type : " + exp.getClass().getName());
    }

    public Type infer(Variable var) {
        return instantiate(env.lookup(var));
    }

    public Type infer(Application app) {
        Type left = infer(app.getLexpr());
        Type right = infer(app.getRexpr());
        TVariable resultType = typeGenerator.freshName();

        unify(left, function(right, resultType));

        return resultType;
    }

    public Type infer(Lambda fun) {
        TVariable paramType = typeGenerator.freshName();
        Type resultType = inEnv(fun.getVariable(), forall(paramType)).infer(fun.getExpression());
        return function(paramType, resultType);
    }

    public Type infer(Let let) {
        Scheme generalizedType = generalize(infer(let.getExpression()));
        Infer localEnv = inEnv(let.getVariable(), generalizedType);
        return localEnv.infer(let.getInExpression());
    }
}
