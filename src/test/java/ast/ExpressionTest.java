package ast;

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
}