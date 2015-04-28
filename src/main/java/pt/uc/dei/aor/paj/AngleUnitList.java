package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class AngleUnitList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<AngleUnit> units;
	private AngleUnit chosenUnit;
	
	public AngleUnitList() {
		units = new ArrayList<>();
		units.add(new AngleUnit("Radianos", 1));
		units.add(new AngleUnit("Graus", 180/Math.PI));
		chosenUnit = units.get(0);
	}

	public List<AngleUnit> getUnits() {
		return units;
	}

	public void setUnits(List<AngleUnit> units) {
		this.units = units;
	}

	public AngleUnit getChosenUnit() {
		return chosenUnit;
	}

	public void setChosenUnit(AngleUnit chosenUnit) {
		this.chosenUnit = chosenUnit;
	}

	
	
}
