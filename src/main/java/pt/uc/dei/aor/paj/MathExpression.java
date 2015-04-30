package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

@Named
@SessionScoped
public class MathExpression implements Serializable {
	private static final long serialVersionUID = 1L;
	private String expression;
	private int reset;
	
	public MathExpression() {
		reset = 0;
		expression = "0";
	}

	
	@Override
	public String toString() {
		return expression;
	}
	

	public boolean evaluate() {
		return evaluate(expression);
	}
	
	public String getExpression() {
		return expression;
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public boolean evaluateScientific(String angleUnit) {
		String exp = convert(angleUnit);
		return evaluate(exp);
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
	
	// TODO -  can be simplified
	public MathExpression getClone() {
		MathExpression exp = new MathExpression();
		
		exp.reset = reset;
		exp.expression = expression;
		
		return exp;
	}
	
	
	public void set(MathExpression exp) {
		List<String> ops = Arrays.asList(new String[]{"*", "+", "/", "-", "^"});
		if (ops.contains(expression.substring(expression.length()-1))) {
			this.expression += "("+exp.expression+")";
		}
		else this.expression = exp.expression;
		reset = 0;
	}
	
	// helper functions
	private String format(String s) {
		if (s.charAt(0) == '.') s = "0"+s;
		int index = s.indexOf('.');
		boolean integer = true;
		if (index != -1) {
			for (int i = index+1; i < s.length(); i++) {
				if (s.charAt(i) != '0') {
					integer = false;
					break;
				}
			}
			
			if (integer) s = s.substring(0, index);
		}
		
		return s;
	}
	
	
	private String convert(String angleUnit) {
//		System.out.println(entries);
//		System.out.println(formExpression(entries));
//		String conversionFactor = null;
//		if (angleUnit.equals("Radianos")) return formExpression(entries);
//		else if (angleUnit.equals("Graus")) conversionFactor = String.valueOf(Math.PI/180)+"*";
//		
//		List<String> trig = Arrays.asList(new String[]{"cos(", "sin(", "tan(", "cosh(", "sinh(", "tanh("});
//		for (String f : trig) {
//			int index = 0;
//			if ((index = entries.indexOf(f)) != -1) {
//				entries.add(index+1, conversionFactor);
//			}
//		}
//		
//		System.out.println(entries);
//		System.out.println(formExpression(entries));
//		return formExpression(entries);
		return "";
	}
	
	private Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

	    @Override
	    public double apply(double... args) {
	        final int arg = (int) args[0];
	        if (arg != args[0]) {
	            throw new IllegalArgumentException("Operand for factorial has to be an integer");
	        }
	        if (arg < 0) {
	            throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
	        }
	        double result = 1;
	        for (int i = 1; i <= arg; i++) {
	            result *= i;
	        }
	        return result;
	    }
	};
	
	private boolean evaluate(String exp) {
		if (reset > 0) return false;
		Expression e;
		try {
			e = new ExpressionBuilder(exp).operator(factorial)
					.variables("\u03C0", "e").build().setVariable("e", Math.E).setVariable("\u03C0", Math.PI);
			try {
				expression = format(String.valueOf(e.evaluate()));
				reset = 1;
				return true;
			}
			catch(ArithmeticException e1) {
				expression = "Divisão por zero";
				reset = 2;
			}
			catch(Exception e1) {
				expression = "Expressão inválida";
				reset = 2;
			}
		}
		catch (Exception e2) {
			expression = "Expressão inválida";
			reset = 2;
		}	
		return false;
	}


	
	public int getReset() {
		return reset;
	}


	public void setReset(int reset) {
		this.reset = reset;
	}
}
