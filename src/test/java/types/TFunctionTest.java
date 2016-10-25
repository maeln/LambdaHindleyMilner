package types;

import ast.Variable;
import inference.Substitution;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static types.TFunction.function;
import static types.TVariable.variable;
import static types.TVariable.variables;

/**
 * Created by valentin on 20/10/2016.
 */
public class TFunctionTest {
    private static final String name = "TEST";

    @Test
    public void createFunction() throws Exception {
        Type left = variable(name);
        Type right = variable(name);

        TFunction fun = function(left, right);

        assertEquals("Should create a fresh function with correct left type", left, fun.left());
        assertEquals("Should create a fresh function with correct right type", right, fun .right());
    }

    @Test
    public void applyNotMatching() throws Exception {
        Substitution sub = new Substitution(variable("NOT MATCHING"), variable("NOT MATCHING"));

        TFunction fun = function(variable(name), variable(name + 1));
        Type result = fun.apply(sub);

        assertEquals("Should not modify anything", fun, result);
    }

    @Test
    public void applyMatching() throws Exception {
        TVariable expected = variable("EXPECTED");
        Substitution sub1 = new Substitution(variable(name), expected);
        Substitution sub2 = new Substitution(variable(name + 1), expected);

        TFunction fun = function(variable(name), variable(name + 1));

        Type result1 = fun.apply(sub1);
        Type result2 = fun.apply(sub2);

        assertEquals("Should apply substitution on right side", function(expected, variable(name + 1)), result1);
        assertEquals("Should apply substitution on left side", function(variable(name), expected), result2);
    }

    @Test
    public void multipleApply() throws Exception {
        TVariable expected = variable("EXPECTED");
        List<TVariable> vars = Arrays.asList(variable(name), variable("NOT MATCHING"), variable(name + 1));
        List<Type> substitutes = Arrays.asList(expected, variable("NOT MATCHING"), expected);
        Substitution sub = new Substitution(vars, substitutes);

        TFunction fun = function(variable(name), variable(name + 1));

        Type result = fun.apply(sub);

        assertEquals("Should apply all substitutions", function(expected, expected), result);
    }

    @Test
    public void ftv() throws Exception {
        List<TVariable> expected = Arrays.asList(variable(name), variable(name + 1));

        TFunction fun = function(variable(name), variable(name + 1));

        HashSet<TVariable> freeVars = fun.ftv();

        assertTrue("Should return ftv of both sides", freeVars.size() == expected.size() && freeVars.containsAll(expected));
    }

    @Test
    public void equals() throws Exception {
        TFunction f1 = function(variable(name), variable(name + 1));
        TFunction f2 = function(variable(name), variable(name + 1));
        TFunction f3 = function(variable(name), variable(name + 2));
        TFunction f4 = function(variable(name + 3), variable(name + 1));
        TFunction f5 = function(variable(name + 3), variable(name + 2));

        assertEquals("Should be equal with both sides are equals", true, f1.equals(f2));
        assertEquals("Shouldn't be equal if rights are different", false, f1.equals(f3));
        assertEquals("Shouldn't be equal if lefts are different", false, f1.equals(f4));
        assertEquals("Shouldn't be equal if both sides are different", false, f1.equals(f5));
    }
}