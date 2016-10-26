package ast;

import inference.environements.TypeInferenceEnv;
import types.TVariable;
import types.Type;

import java.util.Arrays;

import static types.TFunction.function;

/**
 * Represent an Application in Lambda calculus.
 */
public class Application extends Expression {
	private Expression left;
	private Expression right;

	public Application(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}


	@Override
	public Type infer(TypeInferenceEnv env) {
		Type left = getLeft().infer(env);
		Type right = getRight().infer(env);
		TVariable resultType = env.freshName();

		env.unify(left, function(right, resultType));

		return resultType;
	}



	public Expression getLeft() {
		return left;
	}

	public void setLeft(Expression left) {
		this.left = left;
	}

	public Expression getRight() {
		return right;
	}

	public void setRight(Expression right) {
		this.right = right;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Application that = (Application) o;

		return left != null ? left.equals(that.left) : that.left == null
				&& (right != null ? right.equals(that.right) : that.right == null);

	}

	@Override
	public int hashCode() {
		int result = left != null ? left.hashCode() : 0;
		result = 31 * result + (right != null ? right.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "(" + left + ") ◦ (" + right + ")";
	}

	public static Application makeApps(Expression... expr) {
		if(expr.length == 2)
			return new Application(expr[0], expr[1]);
		return new Application(expr[0], makeApps(Arrays.copyOfRange(expr, 1, expr.length)));
	}
}
