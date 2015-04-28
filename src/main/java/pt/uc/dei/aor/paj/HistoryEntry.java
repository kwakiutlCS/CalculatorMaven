package pt.uc.dei.aor.paj;

public class HistoryEntry {
	private MathExpression expression;
	
	
	public HistoryEntry(MathExpression expression) {
		this.expression = expression;
	}


	public MathExpression getExpression() {
		return expression;
	}


	public void setExpression(MathExpression expression) {
		this.expression = expression;
	}


	
	@Override
	public String toString() {
		return expression.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof HistoryEntry) {
			HistoryEntry o = (HistoryEntry) other;
			return expression.equals(o.expression);
		}
		return false;
	}
}
