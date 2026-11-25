package alphabeta;

import java.util.ArrayList;
import java.util.List;

public class State {

	private double alpha;
	private double beta;
	private List<Double> s;
	private List<Double> ds;
	private double last;
	private int nbLefts;
	private int nbRights;

	public State(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
		s = new ArrayList<>();
		s.add(0.0);
		s.add(1.0);
		ds = new ArrayList<>();
		ds.add(1.0);
		last = 1.0;
		nbLefts = 0;
		nbRights = 0;
	}
	
	public double getLast() {
		return last;
	}
	
	public int getNbLefts() {
		return nbLefts;
	}

	public int getNbRights() {
		return nbRights;
	}
	
	public void evolve() {
		int iMax = 0;
		double max = ds.get(iMax);
		for (int i = 1; i < ds.size(); i++) {
			double v = ds.get(i);
			if (v > max) {
				iMax = i;
				max = v; 
			}
		}
		double t1 = s.get(iMax);
		double t2 = s.get(iMax + 1);
		double t = 0.5;
		if (t1 <= last) {
			t = (1.0 - alpha) * t1 + alpha * t2;
			nbLefts++;
		}
		else if (last <= t2) {
			t = (1.0 - beta) * t1 + beta * t2;
			nbRights++;
		}
		else if (t1 > last || t2 < last) {
			System.out.println("t1 = " + t1);
			System.out.println("t2 = " + t2);
			System.out.println("last = " + last);
			throw new IllegalStateException();
		}
		s.add(iMax + 1, t);
		// System.out.println("s = " + s);
		ds.remove(iMax);
		ds.add(iMax, t - t1);
		ds.add(iMax + 1, t2 - t);
		// System.out.println("ds = " + ds);
		last = t;
		// System.out.println("last = " + last);
	}
}
