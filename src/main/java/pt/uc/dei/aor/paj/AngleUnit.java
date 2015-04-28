package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class AngleUnit implements Serializable {
	private static final long serialVersionUID = 1L;
	private String unit;
	private double conversionFactor;
	
	public AngleUnit(String unit, double conversionFactor) {
		this.setConversionFactor(conversionFactor);
		this.unit = unit;
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


	public double getConversionFactor() {
		return conversionFactor;
	}


	public void setConversionFactor(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	
	
}
