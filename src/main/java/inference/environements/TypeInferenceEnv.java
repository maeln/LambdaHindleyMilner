package inference.environements;

import ast.Variable;
import inference.*;
import types.Scheme;
import types.TVariable;
import types.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static types.Scheme.forall;

/**
 * Created by valentin on 25/10/2016.
 */
public class TypeInferenceEnv {
    private TypeGenerator typeGenerator;
    private TypeEnv typeEnv;
    private List<Constraint> constraints;

    public TypeInferenceEnv() {
        typeGenerator = new TypeGenerator();
        typeEnv = new TypeEnv();
        constraints = new LinkedList<>();
    }

    private TypeInferenceEnv(TypeInferenceEnv globalEnv) {
        typeGenerator = globalEnv.typeGenerator;
        typeEnv = new TypeEnv().mergeWith(globalEnv.typeEnv);
        constraints = new LinkedList<>();
    }

    public TypeInferenceEnv inEnv(Variable var, Scheme scheme) {
        TypeInferenceEnv localEnv = new TypeInferenceEnv(this);
        localEnv.bind(var, scheme);
        return localEnv;
    }

    public void bind(Variable var, Scheme scheme) {
        typeEnv.extend(var, scheme);
    }

    public void unify(Type t1, Type t2) {
        constraints.add(new Constraint(t1, t2));
    }

    public Type instantiate(Scheme scheme) {
        List<Substitution> substitutions = new LinkedList<>();

        for (TVariable var : scheme.variables()) {
            substitutions.add(new Substitution(var, typeGenerator.freshName()));
        }

        return scheme.type().apply(substitutions);
    }

    public Scheme generalize(Type t) {
        HashSet<TVariable> freeVars = t.ftv();
        freeVars.removeAll(typeEnv.ftv());
        return forall(new ArrayList<>(freeVars), t);
    }

    public Scheme lookup(Variable variable) {
        return typeEnv.lookup(variable);
    }

    public TVariable freshName() {
        return typeGenerator.freshName();
    }

    public List<Constraint> constraints() {
        return constraints;
    }
}
