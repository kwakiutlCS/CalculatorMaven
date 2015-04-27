package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AngleUnitList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<AngleUnit> units;
	private int numUnits;
	
	public AngleUnitList() {
		numUnits = 1;
		units = new ArrayList<>();
		units.add(new AngleUnit(numUnits++, "Radianos"));
		units.add(new AngleUnit(numUnits++, "Graus"));
	}

	public List<AngleUnit> getUnits() {
		return units;
	}

	public void setUnits(List<AngleUnit> units) {
		this.units = units;
	}
	
}
