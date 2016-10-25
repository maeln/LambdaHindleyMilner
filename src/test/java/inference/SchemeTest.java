package inference;

import org.junit.Test;
import types.Scheme;
import types.TVariable;
import types.Type;

import java.util.*;

import static types.Scheme.forall;
import static org.junit.Assert.*;
import static types.TFunction.function;
import static types.TVariable.variable;

/**
 * Created by valentin on 20/10/2016.
 */
public class SchemeTest {
    private static final String name = "TEST";

    @Test
    public void applyNotMatching() throws Exception {
        Substitution sub1 = new Substitution(variable("NOT MATCHING"), variable("NOT MATCHING"));
        Substitution sub2 = new Substitution(variable(name), variable("NOT MATCHING"));

        Scheme scheme = forall(variable(name), variable(name));
        Scheme result1 = scheme.apply(sub1);
        Scheme result2 = scheme.apply(sub2);

        assertEquals("Should not modify anything", scheme, result1);
        assertEquals("Should not modify anything", scheme, result2);
    }

    @Test
    public void applyMatching() throws Exception {
        Scheme expected = forall(variable(name + 1), variable("EXPECTED"));
        Substitution sub = new Substitution(variable(name), expected.type());

        Scheme scheme = forall(variable(name + 1), variable(name));
        Scheme result = scheme.apply(sub);

        assertEquals("Should modify with the expected variable", expected, result);
    }

    @Test
    public void multipleApply() throws Exception {
        TVariable expected1 = variable("EXPECTED 1");
        TVariable expected2 = variable("EXPECTED 2");
        Scheme expected = forall(variable(name+1), function(expected1, expected2));

        List<TVariable> vars = Arrays.asList(
                variable(name),
                variable("NOT MATCHING"),
                variable(name + 1),
                variable(name + 2)
        );

        List<Type> substitutes = Arrays.asList(
                expected1,
                variable("NOT MATCHING"),
                variable("NOT MATCHING"),
                expected2
        );

        Substitution sub = new Substitution(vars, substitutes);

        Scheme scheme = forall(variable(name + 1), function(variable(name), variable(name + 2)));
        Scheme result = scheme.apply(sub);

        assertEquals("Should modify each branch of function", expected, result);
    }

    @Test
    public void ftv() throws Exception {
        Scheme sch = new Scheme(Collections.singletonList(variable(name)), function(variable(name), variable(name + 1)));
        TVariable[] expected = { variable(name + 1) };

        HashSet<TVariable> freeVars = sch.ftv();

        assertArrayEquals("Should return the correct free variable", expected, freeVars.toArray());
    }

    @Test
    public void equals() throws Exception {
        Scheme sch1 = new Scheme(Collections.emptyList(), variable(name));
        Scheme sch2 = new Scheme(Collections.emptyList(), variable(name));
        Scheme sch3 = new Scheme(Collections.singletonList(variable(name)), variable(name));
        Scheme sch4 = new Scheme(Collections.emptyList(), variable(name + 1));
        Scheme sch5 = new Scheme(Collections.singletonList(variable(name)), variable(name + 1));

        assertTrue("Should be equal if both forall and type are equals", sch1.equals(sch2));
    }

}