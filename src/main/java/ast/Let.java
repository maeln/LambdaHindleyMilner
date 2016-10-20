package ast;

import static org.fusesource.jansi.Ansi.*;

public class Let implements Expression {
	private Variable variable;
	private Expression expression;
	private Expression inExpression;

	public Let(Variable variable, Expression expression, Expression inExpression) {
		this.variable = variable;
		this.expression = expression;
		this.inExpression = inExpression;
	}

	public Let(String variable, Expression expression, Expression inExpression) {
		this.variable = new Variable(variable);
		this.expression = expression;
		this.inExpression = inExpression;
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

	public Expression getInExpression() {
		return inExpression;
	}

	public void setInExpression(Expression inExpression) {
		this.inExpression = inExpression;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Let let = (Let) o;

		if (!variable.equals(let.variable)) return false;
		if (!expression.equals(let.expression)) return false;
		return inExpression.equals(let.inExpression);
	}

	@Override
	public int hashCode() {
		int result = variable != null ? variable.hashCode() : 0;
		result = 31 * result + (expression != null ? expression.hashCode() : 0);
		result = 31 * result + (inExpression != null ? inExpression.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return ansi().fgRed().a("Let ").reset().a(variable).a(" = ").a(expression).fgRed().a(" in ").reset()
				.a(inExpression).toString();
	}
}
