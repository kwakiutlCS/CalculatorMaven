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
	
	public Statistics() {
		symbols = Arrays.asList(new String[]{"+", "-", "*", "/", "%", });
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
		return counter.get(s);
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
	
	
	
}
