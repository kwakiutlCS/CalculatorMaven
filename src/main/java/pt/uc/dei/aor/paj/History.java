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
	private List<MathExpression> expressions;
	
	public History() {
		expressions = new LinkedList<>();
	}
	
	public void addExpression(MathExpression exp) {
		expressions.remove(exp);
		expressions.add(0, exp);
	}
	
	public List<MathExpression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<MathExpression> expressions) {
		this.expressions = expressions;
	}
	
	
}
