package ast;

import inference.environements.TypeInferenceEnv;
import types.TConstructor;
import types.Type;

/**
 * Created by valentin on 25/10/2016.
 */
public abstract class Literal<T> extends Expression {
    private T value;
    private Type type;

    public Literal(T value, TConstructor type){
        this.value = value;
        this.type = type;
    }

    public T value() {
        return value;
    }

    @Override
    public Type infer(TypeInferenceEnv typeInferenceEnv) {
        return type;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
