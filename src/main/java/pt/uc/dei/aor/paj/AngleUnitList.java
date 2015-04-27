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
	
	private List<AngleUnit> unidades = new ArrayList<>();
	private int numUnits = 1;
	
	public AngleUnitList() {
		unidades.add(new AngleUnit(numUnits++, "Radianos"));
		unidades.add(new AngleUnit(numUnits++, "Graus"));
	}

	public List<AngleUnit> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<AngleUnit> unidades) {
		this.unidades = unidades;
	}
	
}
