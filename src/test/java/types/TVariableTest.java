package types;

import inference.Substitution;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static types.TVariable.variable;
import static types.TVariable.variables;

/**
 * Created by valentin on 20/10/2016.
 */
public class TVariableTest {
    private static final String name = "TEST" ;
    private static final int n = 10;

    @Test
    public void createVariable() throws Exception {
        TVariable var = variable(name);
        assertEquals("Should create a variable with the correct name", name, var.name());
    }

    @Test
    public void createVariables() throws Exception {
        String[] names = new String[n];
        for (int i = 0; i < n; i++) names[i] = name + i;

        List<TVariable> vars = variables(names);

        assertEquals("Should create 10 variables", n, vars.size());
        for (int i = 0; i < n; i++) {
            assertEquals("Should create variables with correct name", names[i], vars.get(i).name());
        }
    }

    @Test
    public void applyNotMatching() throws Exception {
        Substitution sub = new Substitution(new TVariable("NOT MATCHING"), new TVariable("NOT MATCHING"));
        TVariable var = variable(name);

        Type result = var.apply(sub);

        assertEquals("Shouldn't change the variable", var, result);
    }

    @Test
    public void applyMatching() throws Exception {
        TVariable expected = variable("MATCHING");
        Substitution sub = new Substitution(variable(name), expected);

        TVariable var = variable(name);
        Type result = var.apply(sub);

        assertEquals("Should replace the variable by the new one.", expected, result);
    }

    @Test
    public void multipleApply() throws Exception {
        TVariable expected = variable("MATCHING FINAL");
        Substitution[] subs = new Substitution[n*2 + 1];
        subs[0] = new Substitution(variable(name), variable("MATCHING 0"));
        for (int i = 0; i < n ; i ++) {
            subs[i * 2 + 1] = new Substitution(variable("NOT MATCHING"), variable("NOT MATCHING"));
            subs[i * 2 + 2] = new Substitution(variable("MATCHING " + i), variable("MATCHING " + (i + 1)));
        }
        subs[n * 2] = new Substitution(variable("MATCHING " + (n - 1)), expected);

        TVariable var = variable(name);
        Type result = var.apply(subs);

        assertEquals("Should have applied all substitutions in the good order", expected, result);
    }

    @Test
    public void ftv() throws Exception {
        TVariable var = variable(name);

        HashSet<TVariable> freeVars = var.ftv();

        assertEquals("Should return exactly 1 ftv", 1, freeVars.size());
        assertEquals("The ftv should be the variable itself", var, freeVars.toArray()[0]);
    }

    @Test
    public void equals() throws Exception {
        TVariable var1 = variable(name);
        TVariable var2 = variable(name);

        assertEquals("Should be equal if names are the same", true, var1.equals(var2));
    }
}