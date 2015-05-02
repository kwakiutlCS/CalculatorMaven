package pt.uc.dei.aor.paj;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class MathHelper {
	private static Function asinh = new Function("asinh", 1) {
		@Override
	    public double apply(double... args) {
	        double a = args[0];
	        return Math.log(a+Math.sqrt(1+a*a));
	    }
	};
	private static Function acosh = new Function("acosh", 1) {
		@Override
	    public double apply(double... args) {
	        double a = args[0];
	        return Math.log(a+Math.sqrt(a*a-1));
	    }
	};
	private static Function atanh = new Function("atanh", 1) {
		@Override
	    public double apply(double... args) {
	        double a = args[0];
	        return Math.log((1+a)/(1-a))/2;
	    }
	};
	private static Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

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
	
	
	public static String evaluateSimple(String expression) {
		try {
			String exp = expression;
			while (exp.indexOf('%') != -1)
				exp = process(exp);
			return evaluate(exp);
		}
		catch (Exception e) {
			return evaluate("1+");
		}
		
	}
	
	public static String evaluateScientific(String expression, String angleUnit) {
		expression = expression.replaceAll("mod", "%");
		try {
			expression = convert(expression, angleUnit);
		}
		catch (Exception e) {
			return evaluate(expression);
		}
		return evaluate(expression);
	}

	
	// helper functions
	private static String process(String exp) {
		int index = exp.indexOf('%');
		
		String p = getLastNumber(exp, index);
		String b = getPreviousValue(exp, index-(p.length()+2));
		double per = new ExpressionBuilder(p).build().evaluate();
		double base = new ExpressionBuilder(b).build().evaluate();

		String res = String.valueOf(base)+exp.substring(b.length(), b.length()+2)+base*per/100+exp.substring(b.length()+p.length()+3);
		
		System.out.println(res);
		return res;
	}
	
	private static String getLastNumber(String exp, int index) {
		int counter = 0;
		int i = index-1;
		char c = exp.charAt(i);
		String v = "";
		
		while (c == '(' || c == ')' || c == 'E' || c == '.' || (c >= '0' && c <= '9') || counter > 0) {
			if (c == '(') counter--;
			else if (c == ')') counter++;
			v = c+v;
			if (i == 0) break;
			c = exp.charAt(--i);
		}
		return v;
		
	}
	
	private static String getPreviousValue(String exp, int index) {
		return exp.substring(0, index);
	}
	
	
	
	
	private static String format(String s) {
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
	

	private static String convert(String expression, String angleUnit) {
		if (angleUnit.equals("Radianos")) return expression;
		
		double factor = Math.PI/180;
		if (angleUnit.equals("Grados")) factor = Math.PI/200;
		String res = expression;
		
		String[] direct = new String[]{" sin", " cos", " tan", " sinh", " cosh", " tanh"};
		String[] inverse = new String[]{" asin", " acos", " atan"};
		for (String f : direct) {
			int lastIndex = 0;
			
			while ((lastIndex = res.indexOf(f, lastIndex)+1) > 0) {
				if (f.length() == 4 && res.indexOf(f+"h", lastIndex-1)+1 == lastIndex) continue;
				String arg = getArg(res, lastIndex);
				res = res.substring(0, lastIndex)+f+"("+factor+"*("+arg+")"+res.substring(lastIndex+f.length()+arg.length());
				lastIndex += arg.length();
			}
			
		}
		
		for (String f : inverse) {
			int lastIndex = 0;
			
			while ((lastIndex = res.indexOf(f, lastIndex)+1) > 0) {
				String fun = getFunction(res, lastIndex);
				res = res.substring(0, lastIndex)+"((1/"+factor+")*"+fun+")"+res.substring(lastIndex+fun.length());
				lastIndex += fun.length();
			}
			
		}
		
		return res;
	}
	
	private static String getArg(String s, int index) {
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
	
	private static String getFunction(String s, int index) {
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
	
	
	
	private static String evaluate(String expression) {
		Expression e;
		try {
			e = new ExpressionBuilder(expression).operator(factorial).function(asinh).function(acosh).function(atanh)
					.variables("\u03C0", "e").build().setVariable("e", Math.E).setVariable("\u03C0", Math.PI);
			try {
				double res = e.evaluate();
				if (Double.isInfinite(res)) expression = "Capacidade excedida";
				else {
					expression = format(String.valueOf(res));
					if (expression.equals("NaN")) expression = "Dados inválidos";
				}
			}
			catch(ArithmeticException e1) {
				expression = "Divisão por zero";
			}
			catch(IllegalArgumentException e1) {
				String m = e1.getMessage();
				if (m.indexOf("Invalid number ") == 0) m = "Expressão inválida";
				expression = m;
			}
			catch(Exception e1) {
				expression = "Expressão inválida";
			}
		}
		catch (Exception e2) {
			expression = "Expressão inválida";
		}	
		return expression;
	}


	public static double[] regression(double[] x, double[] y) {
		double xAvg = avg(x);
		double yAvg = avg(y);
		
		double[] xy = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			xy[i] = x[i]*y[i];
		}
		double xyAvg = avg(xy);
		
		double[] xx = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			xx[i] = x[i]*x[i];
		}
		double xxAvg = avg(xx);
		
		double m = (xAvg*yAvg-xyAvg)/(xAvg*xAvg-xxAvg);
		double b = yAvg - m*xAvg;
				
		// SStotal
		double ssTotal = 0;
		for (int i = 0; i < y.length; i++) {
			ssTotal += (y[i]-yAvg)*(y[i]-yAvg);
		}
		
		// SSres
		double ssRes = 0;
		for (int i = 0; i < y.length; i++) {
			double f = x[i]*m+b;
			ssRes += (y[i]-f)*(y[i]-f);
		}
		
		double R2 = 1 - ssRes/ssTotal;
		return new double[]{m,b,R2};
	}
	
	
	
	public static double avg(double[] x) {
		double total = 0;
		for (double i : x) {
			total += i;
		}
		return total/x.length;
	}
	
	
	
}
