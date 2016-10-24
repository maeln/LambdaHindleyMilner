package inference;

import ast.*;
import org.junit.Test;
import types.TFunction;
import types.TVariable;
import types.Type;

import java.util.HashSet;

import static inference.Scheme.forall;
import static org.junit.Assert.*;
import static types.TFunction.function;
import static types.TVariable.variable;

/**
 * Created by valentin on 24/10/2016.
 */
public class InferTest {
    private final String name = "x";

    @Test
    public void inferVariable() throws Exception {
        Expression var = new Variable(name);
        Infer infer = new Infer();
        infer = infer.inEnv((Variable) var, forall(variable("t")));

        Type t = infer.infer(var);

        assertTrue("Should return a TVariable", t instanceof TVariable);

        TVariable tvar = (TVariable) t;
        assertEquals("Should return the type t", "t", tvar.name());
    }

    @Test
    public void inferLambda() throws Exception {
        Expression exp = new Lambda(new Variable(name), new Variable(name));
        Infer infer = new Infer();

        Type t = infer.infer(exp);

        assertTrue("Should return a TFunction", t instanceof TFunction);
        TFunction fun = (TFunction) t;
        assertEquals("Should return t -> t", fun.left(), fun.right());
    }

    @Test
    public void inferApplication() throws Exception {
        Variable x = new Variable(name);
        Expression exp = new Application(new Lambda(x,x), new Lambda(x,x));

        Infer infer = new Infer();
        Type t = infer.infer(exp);

        assertTrue("Should return a TVariable", t instanceof TVariable);
    }

    @Test
    public void inferLet() throws Exception {
        Expression exp = new Let(new Variable(name), new Lambda(new Variable("x"), new Variable("x")), new Variable(name));
        Infer infer = new Infer();

        Type t = infer.infer(exp);

        assertTrue("Should return a TFunction", t instanceof TFunction);
        TFunction fun = (TFunction) t;
        assertEquals("Should return t -> t", fun.left(), fun.right());
    }
}