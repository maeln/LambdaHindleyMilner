package ast;

import java.util.Arrays;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Lambda implements Expression {
	private Variable variable;
	private Expression expression;

	public Lambda(Variable variable, Expression expression) {
		this.variable = variable;
		this.expression = expression;
	}

	// Utility constructor to be able to type new Lambda("x", ...).
	public Lambda(String variable, Expression expression) {
		this.variable = new Variable(variable);
		this.expression = expression;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Lambda lambda = (Lambda) o;

		return variable != null ? variable.equals(lambda.variable) : lambda.variable == null
				&& (expression != null ? expression.equals(lambda.expression) : lambda.expression == null);

	}

	@Override
	public int hashCode() {
		int result = variable != null ? variable.hashCode() : 0;
		result = 31 * result + (expression != null ? expression.hashCode() : 0);
		return result;
	}

	public String toString() {
		return ansi().fgGreen().a("λ").reset().a(variable).fgBlue().a(" → ").reset().a(expression).toString();
	}

	public static Lambda makeLambdas(Expression expr, String... vars) {
		return makeLambdas(expr, Arrays.asList(vars).stream().map(Variable::new).toArray(Variable[]::new));
	}

	public static Lambda makeLambdas(Expression expr, Variable... vars) {
		if(vars.length == 1)
			return new Lambda(vars[0], expr);
		return new Lambda(vars[0], makeLambdas(expr, Arrays.copyOfRange(vars, 1, vars.length)));
	}
}
