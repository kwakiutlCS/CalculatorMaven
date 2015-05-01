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
		String exp;
		try {
			exp = convert(angleUnit);
		}
		catch (Exception e) {
			return evaluate("3+");
		}
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
		if (angleUnit.equals("Radianos")) return expression;
		
		double factor = Math.PI/180;
		String res = expression;
		
		String[] direct = new String[]{" sin", " cos", " tan", " sinh", " cosh", " tanh"};
		String[] inverse = new String[]{" asin", " acos", " atan"};
		for (String f : direct) {
			int lastIndex = 0;
			
			while ((lastIndex = res.indexOf(f, lastIndex)+1) > 0) {
				String arg = getArg(res, lastIndex);
				res = res.substring(0, lastIndex)+f+"("+factor+"*("+arg+")"+res.substring(lastIndex+f.length()+arg.length());
				lastIndex += arg.length();
			}
			
		}
		
		for (String f : inverse) {
			int lastIndex = 0;
			
			while ((lastIndex = res.indexOf(f, lastIndex)+1) > 0) {
				String arg = getArg(res, lastIndex);
				String fun = getFunction(res, lastIndex);
				System.out.println(fun);
				res = res.substring(0, lastIndex)+"((1/"+factor+")*"+fun+")"+res.substring(lastIndex+fun.length());
				lastIndex += fun.length();
				System.out.println(res);
			}
			
		}
		
		return res;
	}
	
	private String getArg(String s, int index) {
		int begin = index;
		while (s.charAt(begin) != '(') {
			begin++;
		}
		begin++;
		
		int end = begin;
		int counter = 1;
		while (counter > 0) {
			end++;
			if (s.charAt(end) == '(') counter++;
			else if (s.charAt(end) == ')') counter--;
		}
		
		return s.substring(begin, end);
	}
	
	private String getFunction(String s, int index) {
		int end = index;
		int counter = 0;
		boolean start = false;
		while (counter > 0 || !start) {
			end++;
			if (s.charAt(end) == '(') {
				start = true;
				counter++;
			}
			else if (s.charAt(end) == ')') counter--;
		}
		
		return s.substring(index, end);
	}
	
	private Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

	    @Override
	    public double apply(double... args) {
	        final int arg = (int) args[0];
	        if (arg != args[0]) {
	            throw new IllegalArgumentException("Factorial tem de ser inteiro");
	        }
	        if (arg < 0) {
	            throw new IllegalArgumentException("Factorial não pode ser negativo");
	        }
	        else if (arg > 170) {
	        	throw new IllegalArgumentException("Capacidade excedida");
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
				if (expression.equals("NaN")) expression = "Dados inválidos";
				
				return true;
			}
			catch(ArithmeticException e1) {
				expression = "Divisão por zero";
				reset = 2;
			}
			catch(IllegalArgumentException e1) {
				String m = e1.getMessage();
				if (m.indexOf("Invalid number ") == 0) m = "Expressão inválida";
				expression = m;
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
