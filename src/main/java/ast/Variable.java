package ast;

import inference.environements.TypeInferenceEnv;
import types.Type;

public class Variable extends Expression {
	private String name;

	public Variable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public Type infer(TypeInferenceEnv env) {
		return env.instantiate(env.lookup(this));
	}


	//Object override
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Variable variable = (Variable) o;

		return name != null ? name.equals(variable.name) : variable.name == null;

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return name;
	}
}
