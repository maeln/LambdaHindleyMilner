package app;

import ast.*;
import org.fusesource.jansi.AnsiConsole;

public class Main {
	public static void main(String[] args) {
		AnsiConsole.systemInstall();

		// Display a lambda expression.
		Expression root = new Application(
				new Lambda("x", new Variable("x")),
				new Lambda("y", new Variable("y"))
		);

		Expression lambda = Lambda.makeLambdas(root, "s", "d", "g");
		Expression let = new Let("x", lambda, root);
		Expression app = Application.makeApps(lambda, root, lambda, let, root);

		System.out.println(root + " :: " +root.infer());
		/*System.out.println(lambda);
		System.out.println(let);
		System.out.println(app);*/
	}
}
