package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class PassiveAggressive implements Classifier {
	public double[] w;
	public int V;
	public double selfMin, selfMax;

	public PassiveAggressive(int V) {
		this.V = V;
		init();
		initSelfTraining();
	}

	public void init() {
		w = new double[V];
		for (int i = 0; i < w.length; i++)
			w[i] = 0;
	}

	public void initSelfTraining() {
		selfMin = Double.MAX_VALUE;
		selfMax = -Double.MAX_VALUE;
	}

	private int sign(double v) {
		return v < 0 ? -1 : 1;
	}

	double dot(TreeMap<Integer, Double> x) {
		// assert (x.length == w.length);
		double sum = 0;
		for (Map.Entry<Integer, Double> e : x.entrySet()) {
			int i = e.getKey();
			if (i < w.length)
				sum += e.getValue() * w[i];
		}
		return sum;
	}

	private double absSquare(TreeMap<Integer, Double> x) {
		double sum = 0;
		for (Map.Entry<Integer, Double> e : x.entrySet()) {
			int i = e.getKey();
			if (i < w.length)
				sum += e.getValue() * e.getValue();
		}
		return sum;
	}

	/**
	 * 
	 * @param x
	 *            characteristic vector
	 * @param c
	 *            belong class 1 or 0
	 */
	public void add(TreeMap<Integer, Double> x, int c) {
		// assert (x.length == w.length);
		int ybar, y;
		double l, tau;
		ybar = classify(x) ? 1 : -1;
		y = c;
		if (ybar == y)
			return;
		l = Math.max(0, 1 - y * dot(x));
		if (Math.abs(absSquare(x)) > 0.001) {
			tau = l / absSquare(x);
		} else {
			tau = l;
		}
		for (Map.Entry<Integer, Double> e : x.entrySet()) {
			// w[i] = w[i] + tau * y * x[i];
			int i = e.getKey();
			if (i < w.length)
				w[i] = w[i] + tau * y * e.getValue();
		}
		// for (int i = 0; i < w.length; i++)

	}

	/**
	 * 
	 * @param x
	 * @return whether in this class.
	 */
	@Override
	public boolean classify(TreeMap<Integer, Double> x) {
		// assert (x.length == w.length);
		return sign(dot(x)) > 0;
	}

	public double strongClassify(TreeMap<Integer, Double> x) {
		double pc = Math.abs(dot(x));
		return (pc) / (selfMax);
	}

	public void selfTraining(TreeMap<Integer, Double> x, int c) {
		int ybar = classify(x) ? 1 : -1;
		if (ybar == c) {
			double pc = Math.abs(dot(x));
			selfMin = Math.min(selfMin, pc);
			selfMax = Math.max(selfMax, pc);
		}
	}
}
