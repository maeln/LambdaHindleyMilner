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
	public String toString() {
		return lexpr + " . " + rexpr;
	}
}
