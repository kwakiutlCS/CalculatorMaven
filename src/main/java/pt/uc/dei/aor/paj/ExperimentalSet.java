package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ExperimentalSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ExperimentalPoint> points;
	private int numPoints = 0;
	private ExperimentalPoint last;
	@Inject
	private MathExpression exp;
	@Inject
	private Data data;
	private String complete;
	
	public ExperimentalSet() {
		points = new ArrayList<>();
		setComplete("graph");
		last = null;
	}

	public List<ExperimentalPoint> getPoints() {
		return points;
	}

	public void setPoints(List<ExperimentalPoint> points) {
		this.points = points;
	}
	
	public void remove(int id) {
		if (last != null && id == last.getId()) {
			last = null;
			points.remove(points.size()-1);
		}
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getId() == id) {
				points.remove(i);
				break;
			}
		}
	}
	
	public void add() {
		String w = exp.getExpression();
		if (w.charAt(0) == '(') {
			w = w.substring(1, w.length()-1);
		}
		double x = Double.parseDouble(w);
		if (last == null) {
			last = new ExperimentalPoint(x, null, numPoints++);
			points.add(last);
		}
		else {
			last.setY(x);
			Collections.sort(points);
			last = null;
			
		}
		
		exp.clear();
	}

	public String complete() {
		data.clear();
		for (ExperimentalPoint p : points) {
			data.add(p.getX(), p.getY());
		}
		data.complete();
		return "graph";
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getComplete() {
		data.clear();
		for (ExperimentalPoint p : points) {
			data.add(p.getX(), p.getY());
		}
		data.complete();
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}
}
