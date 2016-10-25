package ast;

import inference.Constraint;
import inference.Substitution;
import inference.environements.UnifyEnv;
import inference.interfaces.Inferable;
import inference.environements.TypeInferenceEnv;
import types.Type;

public abstract class Expression implements Inferable<TypeInferenceEnv, Type> {

    @Override
    public final Type infer() {
        TypeInferenceEnv typeInferenceEnv = new TypeInferenceEnv();
        Type t = infer(typeInferenceEnv);

        Substitution s = new Substitution();

        for (Constraint constraint : typeInferenceEnv.constraints()) {
            s = constraint.apply(s).unify().composeWith(s);
        }

        return t.apply(s);
    }
}
