package model;

public class WinnowMachine {
	public double[] f;
	public double threshold;
	public int V;

	public WinnowMachine(int V) {
		this.V = V;
		threshold = V / 512.0;
		init();
	}

	public void init() {
		f = new double[V];
		for (int i = 0; i < f.length; i++)
			f[i] = 1;
	}

	double hFunction(double x[]) {
		assert (x.length == f.length);
		double sum = 0;
		for (int i = 0; i < x.length; i++)
			sum += x[i] * f[i];
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
		assert (x.length == f.length);
		double hx = hFunction(x);
		if (c == 1) {
			if (hx < threshold) {
				for (int i = 0; i < x.length; i++)
					if (x[i] > 0)
						f[i] = f[i] * 2;
			} else {
				return;
			}
		} else {
			if (hx > threshold) {
				for (int i = 0; i < x.length; i++)
					if (x[i] > 0)
						f[i] = f[i] / 2;
			} else {
				return;
			}
		}
	}

	/**
	 * 
	 * @param x
	 * @return whether in this class.
	 */
	public boolean classify(double x[]) {
		assert (x.length == f.length);
		double hx = hFunction(x);
		return hx > threshold;
	}
}
