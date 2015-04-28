package pt.uc.dei.aor.paj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Statistics {
	private Map<String, Integer> counter;
	private List<String> symbols;
	private List<String> descriptions;
	
	public Statistics() {
		descriptions = Arrays.asList(new String[]{"Adição", "Subtracção", "Multiplicação", "Divisão", "Percentagem", "Seno",
				"Coseno", "Tangente", "Logaritmo base 10", "Logaritmo natural", "Seno hiperbólico",
				"Tangente hiperbólico", "Coseno hiperbólico", "Raiz quadrada", "Expoente", "Factorial"});
		symbols = Arrays.asList(new String[]{"+", "-", "*", "/", "%", "sin(", "cos(", "tan(", "log(", "ln(", "sinh(",
				"tanh(", "cosh(", "sqrt(", "^", "!"});
		counter = new HashMap<>();
		for (String s : symbols) {
			counter.put(s, 0);
		}
	}
	
	
	public void add(String s) {
		if (counter.containsKey(s))
			counter.put(s, counter.get(s)+1);
	}


	public int getSymbolCount(String s) {
		return counter.get(symbols.get(descriptions.indexOf(s)));
	}
	
	public Map<String, Integer> getCounter() {
		return counter;
	}


	public List<String> getSymbols() {
		return symbols;
	}


	public void setSymbols(List<String> symbols) {
		this.symbols = symbols;
	}


	public void setCounter(Map<String, Integer> counter) {
		this.counter = counter;
	}


	public List<String> getDescriptions() {
		return descriptions;
	}


	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}
	
	
	
}
