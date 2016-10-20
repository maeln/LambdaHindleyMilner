package inference;

import types.TVariable;
import types.Type;

/**
 * Created by valentin on 18/10/2016.
 */
public class Substitution {
    private final TVariable variable;
    private final Type type;

    public Substitution(TVariable variable, Type type) {
        this.variable = variable;
        this.type = type;
    }

    public TVariable variable() {
        return variable;
    }

    public Type type() {
        return type;
    }
}
