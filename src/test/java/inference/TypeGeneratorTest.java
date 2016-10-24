package inference;

import org.junit.Test;
import types.TVariable;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by valentin on 24/10/2016.
 */
public class TypeGeneratorTest {
    private int n = 100;

    @Test
    public void freshName() throws Exception {
        TypeGenerator gen = new TypeGenerator();
        HashSet<TVariable> vars = new HashSet<>();

        for (int i = 0; i < n; i++) vars.add(gen.freshName());

        assertEquals("Should create 100 unique variable", 100, vars.size());
    }
}