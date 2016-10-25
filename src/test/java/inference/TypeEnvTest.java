package inference;

import ast.Variable;
import inference.environements.TypeEnv;
import org.junit.Test;
import types.Scheme;
import types.TVariable;
import types.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static types.Scheme.forall;
import static org.junit.Assert.*;
import static types.TFunction.function;
import static types.TVariable.variable;
import static inference.environements.TypeEnv.singleton;

/**
 * Created by valentin on 24/10/2016.
 */
public class TypeEnvTest {
    private String name = "TEST";
    private int n = 10;

    @Test
    public void createTypeEnv() throws Exception {
        TypeEnv result = singleton(new Variable(name), forall(variable(name)));

        assertEquals("Should create a singleton (with size 1)", 1, result.variables().size());
        assertEquals("Should create a TypeEnv with the correct Variable", name, result.variables().get(0).getName());
        assertEquals("Should create a TypeEnv with the correct Type", forall(variable(name)), result.lookup(new Variable(name)));
    }

    @Test
    public void extend() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));

        env.extend(new Variable(name + 1), forall(variable(name+1)));

        assertEquals("Should add exactly one binding in the TypEnv", 2, env.variables().size());
        assertTrue("Should add the correct variable in the TypeEnv", env.contains(new Variable(name +1)));
        assertEquals("Should bind the correct type to the variable", forall(variable(name+1)), env.lookup(new Variable(name+1)));
    }

    @Test
    public void extend1() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));

        List<Variable> vars = new LinkedList<>();
        List<Scheme> schemes = new LinkedList<>();

        for(int i = 1; i <= n; i++) {
            vars.add(new Variable(name + i));
            schemes.add(forall(variable(name + i)));
        }

        env.extend(vars, schemes);

        assertEquals("Should have added exactly 10 new bindings", 11, env.variables().size());

        for(int i = 1 ; i <= n ; i++) {
            assertTrue("Should have bound the correct variable", env.contains(new Variable(name + i)));
            assertEquals("Should have bound the correct type", forall(variable(name + i)), env.lookup(new Variable(name + i)));
        }
    }

    @Test
    public void lookup() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));
        List<Variable> vars = new LinkedList<>();
        List<Scheme> schemes = new LinkedList<>();

        for(int i = 1; i <= n; i++) {
            vars.add(new Variable(name + i));
            schemes.add(forall(variable(name + i)));
        }

        env.extend(vars, schemes);

        assertEquals("Should return the correct binding", forall(variable(name)), env.lookup(new Variable(name)));
    }

    @Test
    public void contains() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));
        List<Variable> vars = new LinkedList<>();
        List<Scheme> schemes = new LinkedList<>();

        for(int i = 1; i <= n; i++) {
            vars.add(new Variable(name + i));
            schemes.add(forall(variable(name + i)));
        }

        env.extend(vars, schemes);

        assertTrue("Should return the correct binding", env.contains(new Variable(name)));
    }

    @Test
    public void variables() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));
        List<Variable> vars = new LinkedList<>();
        List<Scheme> schemes = new LinkedList<>();

        for(int i = 1; i <= n; i++) {
            vars.add(new Variable(name + i));
            schemes.add(forall(variable(name + i)));
        }

        env.extend(vars, schemes);

        assertEquals("Should return exactly 11 variable", 11, env.variables().size());

        for(int i = 0 ; i <= n ; i++)
            assertTrue("Should contains all created variables", env.contains(new Variable(name + i)));
    }

    @Test
    public void mergeWith() throws Exception {
        TypeEnv env1 = singleton(new Variable(name), forall(variable(name)));
        TypeEnv env2 = singleton(new Variable(name + 1), forall(variable(name + 1)));
        TypeEnv env3 = singleton(new Variable(name), forall(variable(name + 2)));

        env1.mergeWith(env2);

        assertEquals("Should have aded exactly one binding", 2, env1.variables().size());
        assertTrue("Should have added the correct Variable", env1.contains(new Variable(name+1)));

        env1.mergeWith(env3);

        assertEquals("Should not have modify the TypeEnv", 2, env1.variables().size());
        assertEquals("Should not have modify the first binding", forall(variable(name)), env1.lookup(new Variable(name)));
    }

    @Test
    public void remove() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));

        env.remove(new Variable(name));

        assertEquals("Should have delete the binding", 0, env.variables().size());
    }

    @Test
    public void applyNotMatching() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));

        Substitution sub = new Substitution(variable("NOT MATCHING"), variable("NOT MATCHING"));

        env.apply(sub);

        assertEquals("Should not have modify the TypeEnv", forall(variable(name)), env.lookup(new Variable(name)));
    }

    @Test
    public void applyMatching() throws Exception {
        TypeEnv env = singleton(new Variable(name), forall(variable(name)));

        Scheme expected = forall(variable("EXPECTED"));
        Substitution sub = new Substitution(variable(name), expected.type());

        env.apply(sub);

        assertEquals("Should apply substitution", expected, env.lookup(new Variable(name)));
    }

    @Test
    public void multipleApply() throws Exception {
        Scheme expected1 = forall(variable("EXPECTED 1"));
        Scheme expected2 = forall(variable("EXPECTED 2"));

        List<TVariable> vars = Arrays.asList(
                variable(name),
                variable("NOT MATCHING"),
                variable(name + 1),
                variable(name + 2)
        );

        List<Type> subsitutes = Arrays.asList(
                expected1.type(),
                variable("NOT MATCHING"),
                variable("NOT MATCHING"),
                expected2.type()
        );

        Substitution sub = new Substitution(vars, subsitutes);

        TypeEnv env = singleton(new Variable(name), forall(variable(name)));
        env.extend(
                Arrays.asList(new Variable(name+2), new Variable(name+3)),
                Arrays.asList(forall(variable(name + 2)), forall(variable(name+3)))
        );

        env.apply(sub);

        assertEquals("Should modify first binding", expected1, env.lookup(new Variable(name)));
        assertEquals("Should modify second binding", expected2, env.lookup(new Variable(name+2)));
        assertEquals("Should not modify the last binding", forall(variable(name+3)), env.lookup(new Variable(name+3)));
    }

    @Test
    public void ftv() throws Exception {
        TypeEnv typeEnv = singleton(new Variable(name), forall(variable(name)));
        typeEnv.extend(new Variable(name+1), forall(variable(name+1), function(variable(name + 1), variable(name+2))));

        assertEquals("Should return exactly 2 free variables", 2, typeEnv.ftv().size());
        assertTrue("Should return the correct free variables", typeEnv.ftv().containsAll(Arrays.asList(variable(name), variable(name+2))));
    }

}