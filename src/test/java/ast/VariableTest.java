package ast;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maeln on 20/10/16.
 */
public class VariableTest {

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