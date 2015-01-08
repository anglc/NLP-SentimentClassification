package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import main.Article;
import main.Main;

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

	@Override
	public void training(ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, int kind) {
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTrainArticles);
		articles.addAll(negTrainArticles);

		Main.stdout(String.format("\ncomplete |"), 0);

		for (int it = 0; it < Main.ITLIMIT; it++) {
			if (it % (Main.ITLIMIT / 10) == 0)
				Main.stdout(String.format(">"), 0);
			Collections.shuffle(articles);
			for (int i = 0; i < articles.size(); i++) {
				if (articles.get(i).polarity > 0) {
					this.add(articles.get(i).vec, 1);
				} else {
					this.add(articles.get(i).vec, -1);
				}
			}
		}
		Main.stdout(String.format("|\n\n"), 0);
		Main.stdout(String.format("\ncomplete |"), 0);
		for (int it = 0; it < Main.ITLIMIT; it++) {
			if (it % (Main.ITLIMIT / 10) == 0)
				Main.stdout(String.format(">"), 0);
			Collections.shuffle(articles);
			for (int i = 0; i < articles.size(); i++) {
				if (articles.get(i).polarity > 0) {
					if (kind == 0)
						this.add(articles.get(i).vec, 1);
					else if (kind == 1)
						this.add(articles.get(i).occVec, 1);
				} else {
					if (kind == 0)
						this.add(articles.get(i).vec, -1);
					else if (kind == 1)
						this.add(articles.get(i).occVec, -1);
				}
			}
		}
		Main.stdout(String.format("|\n\n"), 0);

		this.initSelfTraining();
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).polarity > 0) {
				if (kind == 0)
					this.selfTraining(articles.get(i).vec, 1);
				else if (kind == 1)
					this.selfTraining(articles.get(i).occVec, 1);
			} else {
				if (kind == 0)
					this.selfTraining(articles.get(i).vec, -1);
				else if (kind == 1)
					this.selfTraining(articles.get(i).occVec, -1);
			}
		}
	}

	public void training(ArrayList<TreeMap<Integer, Double>> votePosVec,
			ArrayList<TreeMap<Integer, Double>> voteNegVec) {
		Main.stdout(String.format("\ncomplete |"), 0);
		int ratio = 10;
		for (int it = 0; it < Main.ITLIMIT * ratio; it++) {
			if (it % (Main.ITLIMIT * ratio / 10) == 0)
				Main.stdout(String.format(">"), 0);
			int posIdx = 0, negIdx = 0;
			while (posIdx < votePosVec.size() || negIdx < voteNegVec.size()) {
				if (Math.random() < 0.5 && posIdx < votePosVec.size()) {
					this.add(votePosVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < voteNegVec.size()) {
					this.add(voteNegVec.get(negIdx), -1);
					negIdx++;
				}
			}
		}
		Main.stdout(String.format("|\n\n"), 0);
	}
}
