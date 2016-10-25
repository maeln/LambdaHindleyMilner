package ast;

import inference.environements.TypeInferenceEnv;
import org.junit.Test;
import types.TVariable;
import types.Type;

import static types.Scheme.forall;
import static org.junit.Assert.*;
import static types.TVariable.variable;

/**
 * Created by maeln on 20/10/16.
 */
public class VariableTest {

	@Test
	public void infer() throws Exception {
		final String name = "x";
		Expression var = new Variable(name);
		TypeInferenceEnv env = new TypeInferenceEnv();
		env = env.inEnv((Variable) var, forall(variable("t")));

		Type t = var.infer(env);

		assertTrue("Should return a TVariable", t instanceof TVariable);

		TVariable tvar = (TVariable) t;
		assertEquals("Should return the type t", "t", tvar.name());
	}

	@Test
	public void testEquals() throws Exception {
		Variable reference = new Variable("x");
		Variable toTest = new Variable("x");
		assertEquals(reference, toTest);
	}

	@Test
	public void testInequality() throws Exception {
		Variable reference = new Variable("x");
		Variable toTest = new Variable("y");
		assertNotEquals(reference, toTest);
	}
}