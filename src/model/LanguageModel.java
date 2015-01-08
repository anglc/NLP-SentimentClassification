package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import main.Article;

public class LanguageModel {
	/*
	 * wordCategory[category][nGram] = count, get nGram appear in category.
	 * categoryDataCount[category] = count, get #data as views in category.
	 * categoryWordCount[category] = count, get #nGram as views in category.
	 * count(w, c), N_{c}, count(c)
	 */
	public TreeMap<String, TreeMap<nGram, Integer>> wordCategory;
	public TreeMap<String, Integer> categoryDataCount;
	public TreeMap<String, Integer> categoryWordCount;
	public int Ngram, dataCount;
	public double threshold = -69;
	public double selfMin, selfMax;

	public LanguageModel(int n) {
		Ngram = n;
		dataCount = 0;
		wordCategory = new TreeMap<String, TreeMap<nGram, Integer>>();
		categoryDataCount = new TreeMap<String, Integer>();
		categoryWordCount = new TreeMap<String, Integer>();

		wordCategory.put("pos", new TreeMap<nGram, Integer>());
		wordCategory.put("neg", new TreeMap<nGram, Integer>());
		categoryDataCount.put("pos", 0);
		categoryDataCount.put("neg", 0);
		categoryWordCount.put("pos", 0);
		categoryWordCount.put("neg", 0);

		initSelfTraining();
	}

	/**
	 * 
	 * @param s
	 *            a views
	 * @param c
	 *            `pos` or `neg`
	 * @param top
	 *            `DataSieve` best n-gram
	 */
	public void add(TreeMap<nGram, Integer> s, String c) {
		dataCount++;
		TreeMap<nGram, Integer> S = wordCategory.get(c);
		int total = 0;
		for (Map.Entry<nGram, Integer> e : s.entrySet()) {
			total += e.getValue();
			int count = e.getValue();
			if (S.containsKey(e.getKey()))
				count = S.get(e.getKey()) + count;
			S.put(e.getKey(), count);

		}
		categoryDataCount.put(c, categoryDataCount.get(c) + 1);
		categoryWordCount.put(c, categoryWordCount.get(c) + total);
	}

	private double probabilityOfClass(String className, Integer classCount,
			TreeMap<nGram, Integer> record) {
		double Pc, P, count_c, count_w_c;
		TreeMap<nGram, Integer> S = wordCategory.get(className);
		Pc = (double) classCount / dataCount;
		P = Math.log(Pc);
		// P = Pc;
		count_c = categoryDataCount.get(className);
		for (Map.Entry<nGram, Integer> w : record.entrySet()) {
			count_w_c = 0;
			if (S.containsKey(w.getKey()))
				count_w_c = S.get(w.getKey());
			// P = P * (count_w_c + 1) / (count_c + record.size());
			P += Math.log((count_w_c + 1)) - Math.log(count_c + record.size());
		}
		return P;
	}

	public boolean classify(TreeMap<nGram, Integer> record) {
		// s = filter(s);
		double maxPwc = -Double.MAX_VALUE, P;
		String chooseClass = "";
		for (Map.Entry<String, Integer> entry : categoryDataCount.entrySet()) {
			P = probabilityOfClass(entry.getKey(), entry.getValue(), record);
			if (P > maxPwc) {
				maxPwc = P;
				chooseClass = entry.getKey();
			}
		}
		return chooseClass.equals("pos");
	}

	public double strongClassify(TreeMap<nGram, Integer> record,
			String className) {
		int classCount = categoryDataCount.get(className);
		// s = filter(s);

		double P = probabilityOfClass(className, classCount, record);

		return P / selfMax;
	}

	public double strongClassify(TreeMap<nGram, Integer> s) {
		return Math.max(strongClassify(s, "pos"), strongClassify(s, "neg"));
	}

	public void initSelfTraining() {
		selfMin = Double.MAX_VALUE;
		selfMax = -Double.MAX_VALUE;
	}

	public void selfTraining(TreeMap<nGram, Integer> record, String c) {
		// s = filter(s);
		double maxPwc = -Double.MAX_VALUE, P;
		String chooseClass = "";
		for (Map.Entry<String, Integer> entry : categoryDataCount.entrySet()) {
			P = probabilityOfClass(entry.getKey(), entry.getValue(), record);
			if (P > maxPwc) {
				maxPwc = P;
				chooseClass = entry.getKey();
			}
		}
		if (chooseClass.equals(c)) {
			selfMin = Math.min(selfMin, maxPwc);
			selfMax = Math.max(selfMax, maxPwc);
		}
	}

	public void training(ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles) {
		for (Article pos : posTrainArticles)
			this.add(pos.occGramVec, "pos");
		for (Article neg : negTrainArticles)
			this.add(neg.occGramVec, "neg");

		for (Article pos : posTrainArticles)
			this.selfTraining(pos.occGramVec, "pos");
		for (Article neg : negTrainArticles)
			this.selfTraining(neg.occGramVec, "neg");
	}
}
