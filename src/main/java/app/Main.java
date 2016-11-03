package app;

import ast.*;
import ast.lit.Bool;
import ast.lit.Int;
import org.fusesource.jansi.AnsiConsole;

import static app.InferenceApp.infer;

public class Main {
    public static void main(String[] args) {
        AnsiConsole.systemInstall();

        // Display a lambda expression.
        //*
		Expression root =
				new Let("x", new Lambda(new Variable("x"), new Variable("x")),
					new Application(
							new Variable("x"),
							new Lambda("y", new Lambda("z", new Lambda("w", new Application(
									new Variable("y"),
									new Application(
											new Lambda("a", new Application(new Variable("w"), new Variable("a"))),
											new Application(new Variable("z"), new Variable("w"))
									)
							))))
					)
				);
        /*/
        Expression root = new Application(
                new Lambda("x", new Application(
                        new Variable("x"),
                        new Lambda("y", new Lambda("z", new Lambda("w", new Application(
                                new Variable("y"),
                                new Application(
                                        new Lambda("a", new Application(new Variable("w"), new Variable("a"))),
                                        new Application(new Variable("z"), new Variable("w"))
                                )
                        ))))
                )),
                new Lambda("x", new Variable("x"))
        );
        //*/

		// (let f = \x -> x in ((\x -> \y -> y (f (3::Int))) (f True)))
		Expression remi1 = new Bool(true);
		Expression remi2 = new Let("f",new Lambda("x", new Variable("x")),new Application(new Variable("f"),remi1));
		Expression remi3 = new Let("f",new Lambda("x", new Variable("x")),new Application(new Lambda("x", new Lambda("y",new Application(new Variable("y"),new Application(new Variable("f"),new Int(3)))))
				,new Application(new Variable("f"),new Bool(true))));

		// (\f -> \x -> x in ((\x -> \y -> y (f (3::Int))) (f True))) (\z->z)
		Expression remi4 = new Application(new Lambda("f",new Application(new Lambda("x", new Lambda("y",new Application(new Variable("y"),new Application(new Variable("f"),new Int(3)))))
				,new Application(new Variable("f"),new Bool(true)))),new Lambda("x", new Variable("x")));


		infer(remi3);

        Expression lambda = Lambda.makeLambdas(root, "s", "d", "g");
        Expression let = new Let("x", lambda, root);
        Expression app = Application.makeApps(lambda, root, lambda, let, root);

        //infer(root);

		/*System.out.println(lambda);
        System.out.println(let);
		System.out.println(app);*/
    }
}
