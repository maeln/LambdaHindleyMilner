package ast;

import inference.environements.TypeInferenceEnv;
import types.TVariable;
import types.Type;

import java.util.Arrays;

import static types.Scheme.forall;
import static org.fusesource.jansi.Ansi.*;
import static types.TFunction.function;

/**
 * Represent a Lambda expression in Lambda calculus.
 */
public class Lambda extends Expression {
	private Variable variable;
	private Expression expression;

	public Lambda(Variable variable, Expression expression) {
		this.variable = variable;
		this.expression = expression;
	}

	/**
	 * Utility constructor to be able to type new Lambda("x", ...).
	 * @param variable Argument of the Lambda expression.
	 * @param expression Expression (body) of the lambda expression.
 	 */
	public Lambda(String variable, Expression expression) {
		this.variable = new Variable(variable);
		this.expression = expression;
	}



	@Override
	public Type infer(TypeInferenceEnv env) {
		TVariable paramType = env.freshName();
		TypeInferenceEnv localEnv = env.inEnv(getVariable(), forall(paramType));
		Type resultType = getExpression().infer(localEnv);
		return function(paramType, resultType);
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

		if (!variable.equals(lambda.variable)) return false;
		return expression.equals(lambda.expression);
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

	/**
	 * Utility function to be able to write more simply lambda expression like \x,y,z -> ...
	 * @param expr The expression (body) of the lambda expression.
	 * @param vars The variables to use in the lambda.
	 * @return The root lambda.
	 */
	public static Lambda makeLambdas(Expression expr, String... vars) {
		return makeLambdas(expr, Arrays.asList(vars).stream().map(Variable::new).toArray(Variable[]::new));
	}

	/**
	 * Utility function to be able to write more simply lambda expression like \x,y,z -> ...
	 * @param expr The expression (body) of the lambda expression.
	 * @param vars The variables to use in the lambda.
	 * @return The root lambda.
	 */
	public static Lambda makeLambdas(Expression expr, Variable... vars) {
		if(vars.length == 1)
			return new Lambda(vars[0], expr);
		return new Lambda(vars[0], makeLambdas(expr, Arrays.copyOfRange(vars, 1, vars.length)));
	}
}
