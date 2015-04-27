package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class AngleUnit implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String unit;
	
	public AngleUnit() {
		id = 2;
		unit = "Graus";
	}
	
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
