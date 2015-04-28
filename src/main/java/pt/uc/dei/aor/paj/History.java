package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class History implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<HistoryEntry> simpleEntries;
	private List<HistoryEntry> scientificEntries;
	
	public History() {
		simpleEntries = new LinkedList<>();
		scientificEntries = new LinkedList<>();
		
	}
	
	public void addExpression(MathExpression exp, int type) {
		HistoryEntry he = new HistoryEntry(exp);
		
		if (type == 1) {
			simpleEntries.remove(he);
			simpleEntries.add(0, he);
		}
		else {
			scientificEntries.remove(he);
			scientificEntries.add(0, he);
		}
	}

	public List<HistoryEntry> getSimpleEntries() {
		return simpleEntries;
	}

	public void setSimpleEntries(List<HistoryEntry> simpleEntries) {
		this.simpleEntries = simpleEntries;
	}

	public List<HistoryEntry> getScientificEntries() {
		return scientificEntries;
	}

	public void setScientificEntries(List<HistoryEntry> scientificEntries) {
		this.scientificEntries = scientificEntries;
	}

	
	
	
	
}
