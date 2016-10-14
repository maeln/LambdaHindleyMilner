package ast;

public class Application implements Expression {
	private Expression lexpr;
	private Expression rexpr;

	public Application(Expression lexpr, Expression rexpr) {
		this.lexpr = lexpr;
		this.rexpr = rexpr;
	}

	public Expression getLexpr() {
		return lexpr;
	}

	public void setLexpr(Expression lexpr) {
		this.lexpr = lexpr;
	}

	public Expression getRexpr() {
		return rexpr;
	}

	public void setRexpr(Expression rexpr) {
		this.rexpr = rexpr;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Application that = (Application) o;

		return lexpr != null ? lexpr.equals(that.lexpr) : that.lexpr == null
				&& (rexpr != null ? rexpr.equals(that.rexpr) : that.rexpr == null);

	}

	@Override
	public int hashCode() {
		int result = lexpr != null ? lexpr.hashCode() : 0;
		result = 31 * result + (rexpr != null ? rexpr.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return lexpr + " ◦ " + rexpr;
	}
}
