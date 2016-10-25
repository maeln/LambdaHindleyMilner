package ast;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maeln on 20/10/16.
 */
public class LambdaTest {
	@Test
	public void testEquals() throws Exception {
		Lambda reference = new Lambda(new Variable("x"), new Variable("x"));
		Lambda toTest = new Lambda(new Variable("x"), new Variable("x"));
		assertEquals(reference, toTest);

		reference = new Lambda("x", new Variable("x"));
		toTest = new Lambda("x", new Variable("x"));
		assertEquals(reference, toTest);
	}

	@Test
	public void testInequality() throws Exception {
		Lambda reference = new Lambda(new Variable("x"), new Variable("x"));
		Lambda toTest1 = new Lambda(new Variable("y"), new Variable("x"));
		Lambda toTest2 = new Lambda(new Variable("x"), new Variable("y"));
		assertNotEquals(reference, toTest1);
		assertNotEquals(reference, toTest2);
	}

	@Test
	public void testConstructorWithString() throws Exception {
		Variable x = new Variable("x");
		Lambda toTest = new Lambda("x", x);
		Lambda reference = new Lambda(x, x);
		assertEquals(reference, toTest);
	}

	@Test
	public void testMakeLambdasWithString() throws Exception {
		Expression expr = new Application(new Variable("x"), new Variable("y"));
		Lambda reference = new Lambda("x", new Lambda("y", new Lambda("z", expr)));
		Lambda toTest = Lambda.makeLambdas(expr,"x", "y", "z");
		assertEquals(reference, toTest);
	}

	@Test
	public void testMakeLambdasWithVariableClass() throws Exception {
		Expression expr = new Application(new Variable("x"), new Variable("y"));
		Lambda reference = new Lambda(new Variable("x"), new Lambda(new Variable("y"), new Lambda(new Variable("z"), expr)));
		Lambda toTest = Lambda.makeLambdas(expr, new Variable("x"), new Variable("y"), new Variable("z"));
		assertEquals(reference, toTest);
	}
}