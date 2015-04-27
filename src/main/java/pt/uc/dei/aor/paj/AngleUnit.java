package pt.uc.dei.aor.paj;


public class AngleUnit  {
	private int id;
	private String unit;
	
		
	public AngleUnit(int id, String unit) {
		this.id = id;
		this.unit = unit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return unit;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof AngleUnit) {
			AngleUnit o = (AngleUnit) other;
			return id == o.id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
