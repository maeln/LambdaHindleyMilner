package inference.environements;

import types.TVariable;

import static types.TVariable.variable;

/**
 * Created by valentin on 24/10/2016.
 */
public class TypeGenerator {
    private int counter = 0;

    public TVariable freshName() {
        char letter = (char) (97 + counter % 26);
        String name = letter + Integer.toString(counter / 26);
        counter++;
        return variable(name);
    }
}
