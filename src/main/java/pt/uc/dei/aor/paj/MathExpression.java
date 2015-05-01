package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class MathExpression implements Serializable {
	private static final long serialVersionUID = 1L;
	private String expression;
	private int reset;
	
	public MathExpression() {
		clear();
	}

	@Override
	public String toString() {
		return expression;
	}
	
	public boolean evaluate() {
		if (reset > 0) return false;
		String result = MathHelper.evaluateSimple(expression);
		if (result.charAt(0) >= 'A' && result.charAt(0) <= 'Z') reset = 2;
		else reset = 1;
		expression = result;
		return reset == 1;
	}
	
	public boolean evaluateScientific(String angleUnit) {
		if (reset > 0) return false;
		String result = MathHelper.evaluateScientific(expression, angleUnit);
		if (result.charAt(0) >= 'A' && result.charAt(0) <= 'Z') reset = 2;
		else reset = 1;
		expression = result;
		return reset == 1;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof MathExpression) {
			MathExpression o = (MathExpression) other;
			return o.expression.equals(expression);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return expression.hashCode();
	}
	
	public MathExpression getClone() {
		MathExpression exp = new MathExpression();
		exp.reset = reset;
		exp.expression = expression;
		
		return exp;
	}
	
	public void set(MathExpression exp) {
		if (expression.length() > 29) return;
		List<String> ops = Arrays.asList(new String[]{"*", "+", "/", "-", "^"});
		if (ops.contains(expression.substring(expression.length()-1))) {
			this.expression += " ("+exp.expression+")";
		}
		else if (expression.substring(expression.length()-1).equals("("))
			this.expression += " "+exp.expression;
		else this.expression = exp.expression;
		reset = 0;
	}
	
	public int getReset() {
		return reset;
	}

	public void setReset(int reset) {
		this.reset = reset;
	}
	
	public void clear() {
		expression = "0";
		reset = 0;
	}
}
