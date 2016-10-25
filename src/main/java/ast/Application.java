package ast;

import inference.environements.TypeInferenceEnv;
import types.TVariable;
import types.Type;

import java.util.Arrays;

import static types.TFunction.function;

/**
 * Represent an Application in Lambda calculus.
 */
public class Application implements Expression {
	private Expression lexpr;
	private Expression rexpr;

	public Application(Expression lexpr, Expression rexpr) {
		this.lexpr = lexpr;
		this.rexpr = rexpr;
	}


	@Override
	public Type infer(TypeInferenceEnv env) {
		Type left = getLexpr().infer(env);
		Type right = getRexpr().infer(env);
		TVariable resultType = env.freshName();

		env.unify(left, function(right, resultType));

		return resultType;
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
		return "(" + lexpr + " ◦ " + rexpr + ")";
	}

	public static Application makeApps(Expression... expr) {
		if(expr.length == 2)
			return new Application(expr[0], expr[1]);
		return new Application(expr[0], makeApps(Arrays.copyOfRange(expr, 1, expr.length)));
	}
}
