package main;

public class MeasureInfo {
	public double P, R, F1, G;

	public MeasureInfo() {
		this(0, 0);
	}

	public MeasureInfo(double P, double R) {
		double beta = 1;
		this.P = P;
		this.R = R;
		this.F1 = (beta * beta + 1) * P * R / (beta * beta * P + R);
		this.G = Math.sqrt(P * R);
	}

	public MeasureInfo(MeasureInfo a, MeasureInfo b) {
		this((a.P + b.P) / 2, (a.R + b.R) / 2);
	}
}
