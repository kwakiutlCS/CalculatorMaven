package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
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
	private List<String> numbers;
	private List<String> binaryOperators;
	private List<String> unuaryOperators;
	private List<String> functions;
	private List<String> constants;
	private List<String> otherSymbols;
	private String expression;
	private List<String> entries;
	public List<String> getEntries() {
		return entries;
	}


	private int windowSize;
	private int reset;
	
	public MathExpression() {
		windowSize = 37;
		clear();
		reset = 0;
		
		numbers = Arrays.asList(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});
		binaryOperators = Arrays.asList(new String[]{"Div", "Add", "Minus", "Mult", "Exp", "NotCien", "Raiz"});
		unuaryOperators = Arrays.asList(new String[]{"Quad", "Fact", "Perc"});
		functions = Arrays.asList(new String[]{"Sin", "Cos", "Tan", "Sinh", "Cosh", "Tanh", "Sqrt", "Log", "Ln"});
		constants = Arrays.asList(new String[]{"Pi", "Nep"});
		otherSymbols = Arrays.asList(new String[]{"ParD", "ParE"});
	}

	
	@Override
	public String toString() {
		return expression;
	}
	
	
	public void add(String s) {
		if (numbers.contains(s)) {
			addNumber(s);
		}
		else if (binaryOperators.contains(s)) {
			addBinaryOperator(s);
		}
		else if (unuaryOperators.contains(s)) {
			addUnuaryOperator(s);
		}
		else if (s.equals("Dot")) {
			addDot();
		}
		else if (functions.contains(s)) {
			addFunction(s);
		}
		else if (constants.contains(s)) {
			addConstant(s);
		}
		else if (otherSymbols.contains(s)) {
			addSymbols(s);
		}
		
		expression = limitExpressionSize(formExpression(entries));
	}
	
	

	public void evaluate() {
		evaluate(formExpression(entries));
	}
	
	public void evaluateScientific(String angleUnit) {
		String exp = convert(angleUnit);
		evaluate(exp);
	}


	public void clear() {
		clear("0");
	}
	
	public void remove() {
		entries.remove(entries.size()-1);
		if (entries.size() == 0) entries.add("0");
		expression = limitExpressionSize(formExpression(entries));
	}
	
	public void set(MathExpression exp) {
		List<String> opSymbols = Arrays.asList(new String[]{"/", "+", "-", "*", "^", "E", "^(1/("});
		String lastEntry = entries.get(entries.size()-1);
		int index = opSymbols.indexOf(lastEntry);
		if (index == -1) {
			entries = new LinkedList<>();
		}
		else {
			entries.add("(");
		}
		for (String s : exp.entries) {
			entries.add(s);
		}
		if (index != -1) {
			entries.add(")");
		}
		expression = limitExpressionSize(formExpression(entries));
		reset = exp.reset;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MathExpression) {
			MathExpression o = (MathExpression) other;
			return formExpression(o.entries).equals(formExpression(entries));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return entries.hashCode();
	}
	
	public MathExpression getClone() {
		MathExpression exp = new MathExpression();
		exp.entries = new LinkedList<>();
		for (String e : entries) {
			exp.entries.add(e);
		}
		exp.reset = reset;
		exp.expression = expression;
		
		return exp;
	}
	
	// helper functions
	private String formExpression(List<String> e) {
		String exp = "";
		for (String s : e) exp += s;
		return exp;
	}
	
	
	private String limitExpressionSize(String exp) {
		if (exp.length() > windowSize) return exp.substring(exp.length()-windowSize);
		return exp;
	}
	
	
	private void addNumber(String s) {
		if (reset > 0) {
			clear("0");
		}
		if (getLastNumber().equals("0")) {
			entries.set(entries.size()-1, s);
		}
		else {
			entries.add(s);
		}
		reset = 0;
	}
	
	private void addBinaryOperator(String s) {
		if (reset > 1) return;
		List<String> opSymbols = Arrays.asList(new String[]{"/", "+", "-", "*", "^", "E", "^(1/("});
		String lastEntry = entries.get(entries.size()-1);
		String opSymbol = opSymbols.get(binaryOperators.indexOf(s));
		
		if (opSymbols.contains(lastEntry) || lastEntry.equals(".")) {
			entries.set(entries.size()-1, opSymbol);
		}
		else {
			entries.add(opSymbol);
		}
		reset = 0;
	}
	
	private void addUnuaryOperator(String s) {
		if (reset > 1) return;
		List<String> opSymbols = Arrays.asList(new String[]{"^2", "!", "%"});
		String opSymbol = opSymbols.get(unuaryOperators.indexOf(s));
		
		entries.add(opSymbol);
		reset = 0;
	}	
	
	private void addFunction(String s) {
		if (reset > 0) clear("0");
		
		String lastEntry = entries.get(entries.size()-1);
		if (lastEntry.equals(".")) {
			remove();
		}
		lastEntry = entries.get(entries.size()-1);
		if (lastEntry.equals("0")) {
			entries.set(entries.size()-1, s.toLowerCase()+"(");
		}
		else {
			entries.add(s.toLowerCase()+"(");
		}
		reset = 0;
	}
	
	private void addConstant(String s) {
		List<String> symbols = Arrays.asList(new String[]{"\u03C0", "e"});
		String symbol = symbols.get(constants.indexOf(s));
		entries.add(symbol);
		
		reset = 0;
	}

	private void addSymbols(String s) {
		List<String> symbols = Arrays.asList(new String[]{")", "("});
		String symbol = symbols.get(otherSymbols.indexOf(s));
		entries.add(symbol);
		
		reset = 0;
	}
	
	private void addDot() {
		if (reset > 0) {
			clear("0");
		}
		String n = getLastNumber();
		if (n.indexOf(".") == -1) {
			if (n.length() == 0) entries.add("0");
			entries.add(".");
		}
		reset = 0;
	}
	
	private void clear(String s) {
		entries = new LinkedList<>();
		entries.add(s);
		expression = limitExpressionSize(formExpression(entries));
	}
	
	private String getLastNumber() {
		List<String> ops = Arrays.asList(new String[]{"/", "+", "-", "*", "^", "E", "^(1/("});
		
		String number = "";
		for (int i = entries.size()-1; i >= 0 && !ops.contains(entries.get(i)); i--) {
			number = entries.get(i)+number;
		}
		
		return number;
	}
	
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
		System.out.println(entries);
		System.out.println(formExpression(entries));
		String conversionFactor = null;
		if (angleUnit.equals("Radianos")) return formExpression(entries);
		else if (angleUnit.equals("Graus")) conversionFactor = String.valueOf(Math.PI/180)+"*";
		
		List<String> trig = Arrays.asList(new String[]{"cos(", "sin(", "tan(", "cosh(", "sinh(", "tanh("});
		for (String f : trig) {
			int index = 0;
			if ((index = entries.indexOf(f)) != -1) {
				entries.add(index+1, conversionFactor);
			}
		}
		
		System.out.println(entries);
		System.out.println(formExpression(entries));
		return formExpression(entries);
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
	
	private void evaluate(String exp) {
		if (reset > 0) return;
		Expression e;
	
		try {
			e = new ExpressionBuilder(exp).operator(factorial)
					.variables("\u03C0", "e").build().setVariable("e", Math.E).setVariable("\u03C0", Math.PI);
			try {
				String result = format(String.valueOf(e.evaluate()));
				clear(result);
				reset = 1;
			}
			catch(ArithmeticException e1) {
				clear("Divisão por zero!");
				reset = 2;
			}
			catch(Exception e1) {
				clear("Expressão inv�lida");
				reset = 2;
			}
		}
		catch (Exception e2) {
			clear("Expressão inválida");
			reset = 2;
		}	
	}
}
