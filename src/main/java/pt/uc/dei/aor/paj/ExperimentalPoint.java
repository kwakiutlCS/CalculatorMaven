package pt.uc.dei.aor.paj;

public class ExperimentalPoint implements Comparable<ExperimentalPoint> {
	private Double x;
	private Double y;
	private String yV;
	private int id;
	
	public ExperimentalPoint(double x, Double y, int id) {
		this.x = x;
		this.y = y;
		if (y == null)
			this.setyV("");
		else
			this.setyV(String.valueOf(y));
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		this.setyV(String.valueOf(y));
	}
	
	public int getId() { return id; }

	@Override
	public int compareTo(ExperimentalPoint o) {
		if (x > o.x) return 1;
		return -1;
	}
	
	@Override
	public String toString() {
		return x+" -> "+y;
	}

	public String getyV() {
		return yV;
	}

	public void setyV(String yV) {
		this.yV = yV;
	}
}
