package app;

import ast.*;
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

        Expression lambda = Lambda.makeLambdas(root, "s", "d", "g");
        Expression let = new Let("x", lambda, root);
        Expression app = Application.makeApps(lambda, root, lambda, let, root);

        infer(root);

		/*System.out.println(lambda);
        System.out.println(let);
		System.out.println(app);*/
    }
}
