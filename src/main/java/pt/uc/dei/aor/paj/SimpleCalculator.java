package pt.uc.dei.aor.paj;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SimpleCalculator {
	@Inject
	private MathExpression expression;
	@Inject
	private History history;
	@Inject
	private AngleUnitList angleUnitList;
	@Inject
	private Statistics stats;
	
	public SimpleCalculator() {
		
	}

	public void pressKey(ActionEvent event) {
		String id = event.getComponent().getId().substring(3);
		
		MathExpression clone;
		switch(id) {
		case "EqualsSimples": 
			clone = expression.getClone();
			if (expression.evaluate()) {
				history.addExpression(clone, expression.getExpression(), 1);
				stats.add(clone.getExpression());
			}
			break;
		case "EqualsCientifico": 
			clone = expression.getClone();
			if (expression.evaluateScientific(angleUnitList.getChosenUnit())) {
				history.addExpression(clone, expression.getExpression(), 2);
				stats.add(clone.getExpression());
			}
			break;
		}
		
	}
	
	public void reuseExpression(MathExpression exp) {
		expression.set(exp);
	}
	
	public void resetScreen() {
		expression.clear();
	}
	
	
	// getters and setters
	public MathExpression getExpression() {
		return expression;
	}

	public void setExpression(MathExpression expression) {
		this.expression = expression;
	}

	public History getHistory() {
		return history;
	}


	public void setHistory(History history) {
		this.history = history;
	}

	public Statistics getStats() {
		return stats;
	}

	public void setStats(Statistics stats) {
		this.stats = stats;
	}
}
