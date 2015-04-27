package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

public class AngleUnitList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<AngleUnit> units = new ArrayList<>();
	private int numUnits = 1;
	
	public AngleUnitList() {
		units.add(new AngleUnit(numUnits++, "Radianos"));
		units.add(new AngleUnit(numUnits++, "Graus"));
	}

	public List<AngleUnit> getUnits() {
		return units;
	}

	public void setUnits(List<AngleUnit> unidades) {
		this.units = unidades;
	}
	
}
