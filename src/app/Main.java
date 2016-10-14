package app;

import ast.Application;
import ast.Lambda;
import ast.Variable;

public class Main {
	public static void main(String[] args) {
		// Display a lambda expression.
		Application root = new Application(
				new Lambda(
						"x",
						new Lambda(
								"y",
								new Application(
										new Variable("a"),
										new Variable("y")
								)
						)
				),
				new Variable("x")
		);

		System.out.println(root);
	}
}
