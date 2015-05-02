package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.richfaces.model.ChartDataModel.ChartType;
import org.richfaces.model.NumberChartDataModel;

@Named
@SessionScoped
public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private NumberChartDataModel model;
	private NumberChartDataModel reg;
	
	public Data() {
		clear();
	}
	
	public void add(double x, double y) {
		model.put(x, y);
	}
	
	
	
	public String getName() {
		return name;
	}

	public void clear() {
		model = new NumberChartDataModel(ChartType.line);
		reg = new NumberChartDataModel(ChartType.line);
		this.name = "graph";
	}
	
	public NumberChartDataModel getModel() { return model; }
	public NumberChartDataModel getReg() { return reg; }
	
	
	public void complete() {
		Map<Number, Number> data = model.getData();
		int size = data.size();
		double[] x = new double[size];
		double[] y = new double[size];
		
		int i = 0;
		for (Number d : data.keySet()) {
			x[i] = d.doubleValue();
			y[i++] = data.get(d).doubleValue();
		}
		
		double[] par = MathHelper.regression(x, y);
		
		for (int j = 0; j < x.length; j++) {
			reg.put(x[j], par[0]*x[j]+par[1]);
		}
	}

}
