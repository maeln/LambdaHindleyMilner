package ast;

import ast.lit.Int;
import exceptions.InfiniteTypeException;
import exceptions.UnboundVariableException;
import org.junit.Test;
import types.TFunction;
import types.TVariable;
import types.Type;

import static org.junit.Assert.*;

/**
 * Created by valentin on 25/10/2016.
 */
public class ExpressionTest {

    @Test
    public void inferIdentity() throws Exception {
        Expression exp = new Lambda("x", new Variable("x"));

        Type t = exp.infer();

        assertTrue("Should return a type of Function", t instanceof TFunction);
        TFunction f = (TFunction) t;

        assertTrue("Should have the type t -> t", f.left() instanceof TVariable &&  f.left().equals(f.right()));
    }

    @Test(expected = InfiniteTypeException.class)
    public void inferInfiniteType() throws Exception {
        Variable x = new Variable("x");
        Expression exp = new Lambda(x, new Application(x, x));
        exp.infer();
    }

    @Test(expected = UnboundVariableException.class)
    public void inferUnboundVariable() throws Exception {
        Expression exp = new Lambda("x", new Variable("z"));
        exp.infer();
    }
}