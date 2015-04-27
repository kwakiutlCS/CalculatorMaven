package pt.uc.dei.aor.paj;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

@Named
@SessionScoped
public class SimpleCalculator implements Serializable {
	private static final long serialVersionUID = 1L;
	private MathExpression expression;
	private String angleUnit;
	
	public SimpleCalculator() {
		System.out.println("calculator");
		expression = new MathExpression();
		angleUnit = "Radianos";
	}

	
	public void pressKey(ActionEvent event) {
		String id = event.getComponent().getId().substring(3);
		System.out.println(id);
		switch(id) {
		case "Clear": expression.clear(); break;
		case "Back": expression.remove(); break;
		case "EqualsSimples": expression.evaluate(); break;
		case "EqualsCientifico": expression.evaluateScientific(angleUnit); break;
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


	public String getAngleUnit() {
		return angleUnit;
	}


	public void setAngleUnit(String angleUnit) {
		this.angleUnit = angleUnit;
	}
	
}
