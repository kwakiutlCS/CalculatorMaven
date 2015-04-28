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
		System.out.println("key");
		System.out.println(id);
		switch(id) {
		case "Clear": expression.clear(); break;
		case "Back": expression.remove(); break;
		case "EqualsSimples": 
			history.addExpression(expression.getClone(), 1);
			for (String s : expression.getEntries()) {
				stats.add(s);
			}
			expression.evaluate(); 
			
			System.out.println(stats.getCounter().get("+"));
			break;
		case "EqualsCientifico": 
			history.addExpression(expression.getClone(), 2);
			expression.evaluateScientific(angleUnitList.getChosenUnit()); break;
		default: expression.add(id); break;
		}
		
	}
	
	
	public void reuseExpression(MathExpression exp) {
		expression.set(exp);
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



	
}
