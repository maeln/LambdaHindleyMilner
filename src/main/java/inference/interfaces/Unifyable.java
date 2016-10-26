package inference.interfaces;

import inference.Constraint;
import inference.Substitution;
import exceptions.InfiniteTypeException;
import types.TVariable;
import types.Type;

import java.util.List;

/**
 * Created by valentin on 25/10/2016.
 */
public interface Unifyable {
    Substitution unifyWith(Type type);

    default Substitution bind(TVariable var, Type type) {
        if(var.equals(type)) return new Substitution();
        if(type.ftv().contains(var)) throw new InfiniteTypeException(this, type);
        return new Substitution(var, type);
    }
}
