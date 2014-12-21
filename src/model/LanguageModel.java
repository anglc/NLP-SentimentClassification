package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

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
	public void add(String s, String c, ArrayList<nGram> top) {
		dataCount++;
		ArrayList<nGram> t = ModelUtilities.transformNgram(s, Ngram);
		TreeMap<nGram, Integer> record = new TreeMap<nGram, Integer>();
		TreeMap<nGram, Integer> S = wordCategory.get(c);
		for (nGram e : t) {
			int count = 1;
			if (record.containsKey(e))
				count = record.get(e) + 1;
			record.put(e, count);
		}

		int total = 0;
		for (nGram e : top) {
			if (record.containsKey(e)) {
				int wcount = record.get(e), count;
				count = wcount;
				total += wcount;
				if (S.containsKey(e))
					count = S.get(e) + wcount;
				S.put(e, count);
			}
		}
		categoryDataCount.put(c, categoryDataCount.get(c) + 1);
		categoryWordCount.put(c, categoryWordCount.get(c) + total);
	}

	public String filter(String s) {
		String fs = "";
		String[] stmt = s.split("\\.|,|:");
		for (String ss : stmt) {
			ArrayList<nGram> t = ModelUtilities.transformNgram(ss, Ngram);
			TreeMap<nGram, Integer> record = new TreeMap<nGram, Integer>();
			for (nGram e : t) {
				int count = 1;
				if (record.containsKey(e))
					count = record.get(e) + 1;
				record.put(e, count);
			}
			double maxPwc = -1e+30;
			String chooseClass = "";

			for (Map.Entry<String, Integer> entry : categoryDataCount
					.entrySet()) {
				double Pc, P, count_c, count_w_c;
				TreeMap<nGram, Integer> S = wordCategory.get(entry.getKey());
				Pc = (double) entry.getValue() / dataCount;
				P = Math.log(Pc);
				count_c = categoryDataCount.get(entry.getKey());
				for (Map.Entry<nGram, Integer> w : record.entrySet()) {
					count_w_c = 0;
					if (S.containsKey(w.getKey()))
						count_w_c = S.get(w.getKey());
					P += Math.log((double) (count_w_c + 1)
							/ (count_c + record.size()));
				}
				if (P > maxPwc) {
					maxPwc = P;
					chooseClass = entry.getKey();
				}
			}
			if (maxPwc > threshold)
				fs += ss + " . ";
		}
		return fs;
	}

	public boolean classify(String s, ArrayList<nGram> top) {
		s = filter(s);
		ArrayList<nGram> t = ModelUtilities.transformNgram(s, Ngram);
		TreeMap<nGram, Integer> record = new TreeMap<nGram, Integer>();
		for (nGram e : t) {
			int count = 1;
			if (record.containsKey(e))
				count = record.get(e) + 1;
			record.put(e, count);
		}
		double maxPwc = -1e+30;
		String chooseClass = "";
		for (Map.Entry<String, Integer> entry : categoryDataCount.entrySet()) {
			double Pc, P, count_c, count_w_c;
			TreeMap<nGram, Integer> S = wordCategory.get(entry.getKey());
			Pc = (double) entry.getValue() / dataCount;
			P = Math.log(Pc);
			count_c = categoryDataCount.get(entry.getKey());
			for (Map.Entry<nGram, Integer> w : record.entrySet()) {
				count_w_c = 0;
				if (S.containsKey(w.getKey()))
					count_w_c = S.get(w.getKey());
				P += Math.log((double) (count_w_c + 1)
						/ (count_c + record.size()));
			}
			if (P > maxPwc) {
				maxPwc = P;
				chooseClass = entry.getKey();
			}
		}
		return chooseClass.equals("pos");
	}
}
