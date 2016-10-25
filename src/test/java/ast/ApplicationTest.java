package ast;

import org.junit.Test;
import types.TVariable;
import types.Type;

import static org.junit.Assert.*;

/**
 * Created by maeln on 20/10/16.
 */
public class ApplicationTest {

	@Test
	public void infer() throws Exception {
		Variable x = new Variable("x");
		Expression exp = new Application(new Lambda(x,x), new Lambda(x,x));

		Type t = exp.infer();

		assertTrue("Should return a TVariable", t instanceof TVariable);
	}

	@Test
	public void testEquals() throws Exception {
		Application reference = new Application(new Lambda("x", new Variable("y")), new Lambda("x", new Variable("x")));
		Application toTest = new Application(new Lambda("x", new Variable("y")), new Lambda("x", new Variable("x")));
		assertEquals(reference, toTest);
	}

	@Test
	public void testInequality() throws Exception {
		Application reference = new Application(new Variable("x"), new Variable("y"));
		Application toTest = new Application(new Variable("y"), new Variable("x"));
		assertNotEquals(reference, toTest);
	}

	@Test
	public void testMakeApps() throws Exception {
		Expression lambda1 = new Lambda("x", new Application(new Variable("x"), new Variable("y")));
		Expression lambda2 = new Lambda("x", new Variable("x"));
		Expression let = new Let("z", lambda1, lambda2);

		Application toTest = Application.makeApps(let, lambda1, lambda2);
		Application reference = new Application(let, new Application(lambda1, lambda2));
		assertEquals(reference, toTest);
	}
}