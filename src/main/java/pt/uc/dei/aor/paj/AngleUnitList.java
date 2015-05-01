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
	
	private List<String> units;
	private String chosenUnit;
	
	public AngleUnitList() {
		units = new ArrayList<>();
		units.add("Radianos");
		units.add("Graus");
		units.add("Grados");
		chosenUnit = "Radianos";
	}

	public List<String> getUnits() {
		return units;
	}

	public void setUnits(List<String> units) {
		this.units = units;
	}

	public String getChosenUnit() {
		return chosenUnit;
	}

	public void setChosenUnit(String chosenUnit) {
		this.chosenUnit = chosenUnit;
	}
}
