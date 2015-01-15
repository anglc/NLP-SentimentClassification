package comp;

public class MeasureInfo {
	public static final int MAX = 1, MIN = 0;
	public double P, R, F1, G;

	public MeasureInfo() {
		this(0, 0);
	}

	public MeasureInfo(int TYPE) {
		if (TYPE == MAX) {
			this.P = MAX;
			this.R = MAX;
			this.F1 = MAX;
		}
		if (TYPE == MIN) {
			this.P = MIN;
			this.R = MIN;
			this.F1 = MIN;
		}
	}

	public MeasureInfo(double P, double R) {
		double beta = 1;
		this.P = P;
		this.R = R;
		this.F1 = (beta * beta + 1) * P * R / (beta * beta * P + R);
		this.G = Math.sqrt(P * R);
	}

	public MeasureInfo(MeasureInfo a, MeasureInfo b) {
		double beta = 1;
		this.P = (a.P + b.P) / 2;
		this.R = (a.R + b.R) / 2;
		this.F1 = (beta * beta + 1) * P * R / (beta * beta * P + R);
	}

	public MeasureInfo max(MeasureInfo tmp) {
		return tmp.P > P ? tmp : this;
	}

	public MeasureInfo min(MeasureInfo tmp) {
		return tmp.P < P ? tmp : this;
	}

	public MeasureInfo add(MeasureInfo tmp) {
		MeasureInfo ret = new MeasureInfo();
		ret.P = P + tmp.P;
		ret.R = R + tmp.R;
		ret.F1 = F1 + tmp.F1;
		return ret;
	}
}
