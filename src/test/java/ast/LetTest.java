package ast;

import org.junit.Test;
import types.TFunction;
import types.Type;

import static org.junit.Assert.*;

/**
 * Created by maeln on 20/10/16.
 */
public class LetTest {

	@Test
	public void infer() throws Exception {
		final String name = "x";
		Expression exp = new Let(new Variable(name), new Lambda(new Variable(name), new Variable(name)), new Variable(name));

		Type t = exp.infer();

		assertTrue("Should return a TFunction", t instanceof TFunction);
		TFunction fun = (TFunction) t;
		assertEquals("Should return t -> t", fun.left(), fun.right());
	}

	@Test
	public void testEquals() throws Exception {
		Let reference = new Let(new Variable("z"), new Variable("x"), new Variable("y"));
		Let toTest = new Let(new Variable("z"), new Variable("x"), new Variable("y"));
		assertEquals(reference, toTest);
	}

	@Test
	public void testInequality() throws Exception {
		Let reference = new Let(new Variable("z"), new Variable("x"), new Variable("y"));
		Let toTest1 = new Let(new Variable("p"), new Variable("x"), new Variable("y"));
		Let toTest2 = new Let(new Variable("z"), new Variable("p"), new Variable("y"));
		Let toTest3 = new Let(new Variable("z"), new Variable("x"), new Variable("p"));
		assertNotEquals(reference, toTest1);
		assertNotEquals(reference, toTest2);
		assertNotEquals(reference, toTest3);
	}

	@Test
	public void testConstructorWithString() throws Exception {
		Expression exp1 = new Variable("x");
		Expression exp2 = new Variable("y");
		Let reference = new Let(new Variable("z"), exp1, exp2);
		Let toTest = new Let("z", exp1, exp2);
		assertEquals(reference, toTest);
	}
}