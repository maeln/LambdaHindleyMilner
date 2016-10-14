package ast;

public class Lambda {
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
		return "\\" + variable + " -> " + expression;
	}
}
