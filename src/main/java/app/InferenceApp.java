package app;

import ast.Expression;
import exceptions.InfiniteTypeException;
import exceptions.UnboundVariableException;
import exceptions.UnificationFailException;
import types.Type;

/**
 * Created by valentin on 25/10/2016.
 */
public class InferenceApp {
    static void infer(Expression exp) {
        try {
            Type t = exp.infer();
            System.out.println(exp + " :: " + t);
        }
        catch (InfiniteTypeException | UnboundVariableException | UnificationFailException e) {
            System.out.println("Type error in " + exp + " : " + e.getMessage());
        }
    }
}
