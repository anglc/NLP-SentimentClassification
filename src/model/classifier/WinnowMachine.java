package model.classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import main.Main;
import model.Article;

public class WinnowMachine implements Classifier {
	public double[] f;
	public double threshold;
	public int V;
	public int ITLIMIT;
	public double selfMin, selfMax;

	public WinnowMachine(int V) {
		this.V = V;
		threshold = V / 512;
		init();
		initSelfTraining();
		this.ITLIMIT = DEFAULT_ITLIMIT;
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

	double hFunction(TreeMap<Integer, Double> x) {
		// assert (x.length == f.length);
		double sum = 0;
		for (Map.Entry<Integer, Double> e : x.entrySet()) {
			int i = e.getKey();
			if (i < f.length)
				sum += f[i] * e.getValue();
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
		// assert (x.length == f.length);
		double hx = hFunction(x);
		if (c == 1) {
			if (hx < threshold) {
				for (Map.Entry<Integer, Double> e : x.entrySet()) {
					int i = e.getKey();
					if (i < f.length && e.getValue() > 0)
						f[i] *= 2;
				}
			} else {
				return;
			}
		} else {
			if (hx > threshold) {
				for (Map.Entry<Integer, Double> e : x.entrySet()) {
					int i = e.getKey();
					if (i < f.length && e.getValue() > 0)
						f[i] /= 2;
				}
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
	public boolean classify(TreeMap<Integer, Double> x) {
		// assert (x.length == f.length);
		double hx = hFunction(x);
		return hx > threshold;
	}

	public double strongClassify(TreeMap<Integer, Double> x) {
		double hx = Math.abs(hFunction(x) - threshold);
		return (hx) / (selfMax);
	}

	public void selfTraining(TreeMap<Integer, Double> x, int c) {
		int ybar = classify(x) ? 1 : -1;
		if (ybar == c) {
			double pc = Math.abs(hFunction(x) - threshold);
			selfMin = Math.min(selfMin, pc);
			selfMax = Math.max(selfMax, pc);
		}
	}

	@Override
	public void training(ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, int kind) {
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTrainArticles);
		articles.addAll(negTrainArticles);
		Main.stdout(String.format("\ncomplete |"), 0);

		for (int it = 0; it < ITLIMIT; it++) {
			if (it % (ITLIMIT / 10) == 0)
				Main.stdout(String.format(">"), 0);
			Collections.shuffle(articles);
			for (int i = 0; i < articles.size(); i++) {
				if (articles.get(i).polarity > 0) {
					this.add(articles.get(i).vec, 1);
				} else {
					this.add(articles.get(i).vec, 0);
				}
			}
		}
		Main.stdout(String.format("|\n\n"), 0);

		this.initSelfTraining();
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).polarity > 0) {
				this.selfTraining(articles.get(i).vec, 1);
			} else {
				this.selfTraining(articles.get(i).vec, 0);
			}
		}

	}

	@Override
	public void setLimited(TreeSet<Integer> limitSet) {
		// TODO Auto-generated method stub

	}
}
