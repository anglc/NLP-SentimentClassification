package model;

public class WinnowMachine implements Classifier {
	public double[] f;
	public double threshold;
	public int V;
	public double selfMin, selfMax;

	public WinnowMachine(int V) {
		this.V = V;
		threshold = V / 512;
		init();
		initSelfTraining();
	}

	public void init() {
		f = new double[V];
		for (int i = 0; i < f.length; i++)
			f[i] = 1;
	}

	public void initSelfTraining() {
		selfMin = Double.MAX_VALUE;
		selfMax = -Double.MAX_VALUE;
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
	@Override
	public boolean classify(double x[]) {
		assert (x.length == f.length);
		double hx = hFunction(x);
		return hx > threshold;
	}

	public double strongClassify(double x[]) {
		double hx = Math.abs(hFunction(x) - threshold);
		return (hx - selfMin) / (selfMax - selfMin);
	}

	public void selfTraining(double x[], int c) {
		int ybar = classify(x) ? 1 : -1;
		if (ybar == c) {
			double pc = Math.abs(hFunction(x) - threshold);
			selfMin = Math.min(selfMin, pc);
			selfMax = Math.max(selfMax, pc);
		}
	}
}
