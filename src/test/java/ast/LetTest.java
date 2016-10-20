package ast;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maeln on 20/10/16.
 */
public class LetTest {
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