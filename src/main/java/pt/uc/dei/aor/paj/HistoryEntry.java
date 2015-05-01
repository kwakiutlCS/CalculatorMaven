package pt.uc.dei.aor.paj;

public class HistoryEntry {
	private MathExpression expression;
	private String result;
	
	
	public HistoryEntry(MathExpression expression, String result) {
		this.expression = expression;
		String s = result;
		if (s.length() > 8) s = s.substring(0, 5)+"...";
		this.setResult(s);
	}


	public MathExpression getExpression() {
		return expression;
	}


	public void setExpression(MathExpression expression) {
		this.expression = expression;
	}


	
	@Override
	public String toString() {
		String s = expression.toString();
		int lim = 31;
		if (s.length() > lim) s = s.substring(0, lim-3)+"...";
		return s;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof HistoryEntry) {
			HistoryEntry o = (HistoryEntry) other;
			return expression.equals(o.expression);
		}
		return false;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}
}
