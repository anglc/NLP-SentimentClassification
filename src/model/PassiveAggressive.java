package model;

public class PassiveAggressive {
	public double[] w;
	public double threshold;
	public int V;

	public PassiveAggressive(int V) {
		this.V = V;
		threshold = V / 512.0;
		init();
	}

	public void init() {
		w = new double[V];
		for (int i = 0; i < w.length; i++)
			w[i] = 0;
	}

	private int sign(double v) {
		return v < 0 ? -1 : 1;
	}

	double dot(double x[]) {
		assert (x.length == w.length);
		double sum = 0;
		for (int i = 0; i < x.length; i++)
			sum += x[i] * w[i];
		return sum;
	}

	private double absSquare(double[] x) {
		double sum = 0;
		for (int i = 0; i < x.length; i++)
			sum += x[i] * x[i];
		return sum;
	}

	/**
	 * 
	 * @param x
	 *            characteristic vector
	 * @param c
	 *            belong class 1 or 0
	 */
	public void add(double x[], int c) {
		assert (x.length == w.length);
		int ybar, y;
		double l, tau;
		ybar = classify(x);
		y = c;
		if (ybar == y)	return;
		l = Math.max(0, 1 - y * dot(x));
		if (Math.abs(absSquare(x)) > 0.1) {
			tau = l / absSquare(x);
		} else {
			tau = l;
		}
		for (int i = 0; i < w.length; i++)
			w[i] = w[i] + tau * y * x[i];
	}

	/**
	 * 
	 * @param x
	 * @return whether in this class.
	 */
	public int classify(double x[]) {
		assert (x.length == w.length);
		return sign(dot(x));
	}
}
