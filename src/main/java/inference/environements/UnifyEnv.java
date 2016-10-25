package inference.environements;

import exceptions.InfiniteTypeException;
import inference.Constraint;
import inference.Substitution;
import types.TVariable;
import types.Type;

import java.util.List;

/**
 * Created by valentin on 25/10/2016.
 */
public class UnifyEnv {
    private Substitution sub = new Substitution();
    private List<Constraint> constraints;

    public UnifyEnv(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public Substitution bind(TVariable var, Type type) {
        if(var.equals(type)) return new Substitution();
        if(type.ftv().contains(var)) throw new InfiniteTypeException();
        return new Substitution(var, type);
    }
}
