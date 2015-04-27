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
	private AngleUnit angleUnit;
	
	public SimpleCalculator() {
		angleUnit = new AngleUnit(1, "Radianos");
	}

	
	public void pressKey(ActionEvent event) {
		String id = event.getComponent().getId().substring(3);
		System.out.println(id);
		switch(id) {
		case "Clear": expression.clear(); break;
		case "Back": expression.remove(); break;
		case "EqualsSimples": 
			history.addExpression(expression.getClone());
			expression.evaluate(); 
			break;
		case "EqualsCientifico": expression.evaluateScientific(angleUnit.getUnit()); break;
		default: expression.add(id); break;
		}
		
	}
	
	
	
	// getters and setters
	public MathExpression getExpression() {
		return expression;
	}

	public void setExpression(MathExpression expression) {
		this.expression = expression;
	}


	public AngleUnit getAngleUnit() {
		return angleUnit;
	}


	public void setAngleUnit(AngleUnit angleUnit) {
		this.angleUnit = angleUnit;
	}


	public History getHistory() {
		return history;
	}


	public void setHistory(History history) {
		this.history = history;
	}
	
}
