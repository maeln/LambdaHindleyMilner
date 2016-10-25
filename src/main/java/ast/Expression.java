package ast;

import inference.interfaces.Inferable;
import inference.environements.TypeInferenceEnv;
import types.Type;

public interface Expression extends Inferable<TypeInferenceEnv, Type> {
    default Type infer() {
        return infer(new TypeInferenceEnv());
    }
}
