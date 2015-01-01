package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataSieve {
	private int n;
	private String[] views, otherViews;
	private TreeMap<nGram, Integer> viewsMap, otherMap; // total appear count
	private TreeMap<nGram, Integer> viewsCount, otherCount; // #appear in views
	private ArrayList<nGram> xsquare;
	public int ngramCount = 0;

	/**
	 * 
	 * @param n
	 *            n-grams
	 * @param views
	 *            main class
	 * @param otherViews
	 *            other class
	 */
	public DataSieve(int n, String[] views, String[] otherViews) {
		this.n = n;
		this.views = views;
		this.otherViews = otherViews;
		sieve();
	}

	public void setViews(String[] views) {
		this.views = views;
	}

	public String[] getViews() {
		return views;
	}

	public void storeNgram(ArrayList<nGram> t, int kind) {
		nGram e;
		TreeSet<nGram> set = new TreeSet<nGram>();
		int count;
		for (int i = 0; i < t.size(); i++) {
			e = t.get(i);
			count = 1;
			set.add(e);
			if (kind == 1) {
				if (viewsMap.containsKey(e)) {
					count = viewsMap.get(e) + 1;
				}
				viewsMap.put(e, count);
			} else {
				if (otherMap.containsKey(e)) {
					count = otherMap.get(e) + 1;
				}
				otherMap.put(e, count);
			}
		}
		for (nGram ee : set) {
			count = 1;
			if (kind == 1) {
				if (viewsCount.containsKey(ee)) {
					count = viewsCount.get(ee) + 1;
				}
				viewsCount.put(ee, count);
			} else {
				if (otherCount.containsKey(ee)) {
					count = otherCount.get(ee) + 1;
				}
				otherCount.put(ee, count);
			}
		}
	}

	/**
	 * debug example: Score 5.402974 (one of the)
	 * 
	 * @param k
	 *            top-k n-gram
	 */
	public void printBestNgram(int k) {
		for (int i = 0; i < k && i < xsquare.size(); i++) {
			nGram e = xsquare.get(i);
			System.out.printf("Score %f (", e.score);
			for (int j = 0; j < e.iWord.length; j++) {
				System.out
						.printf("%s ", ModelUtilities.getWordName(e.iWord[j]));
			}
			System.out.println(")");
		}
	}

	public ArrayList<nGram> getBestNgram(int k) {
		SubGramSet subNgram = new SubGramSet();
		ArrayList<nGram> ret = new ArrayList<nGram>();
		for (int i = 0; ret.size() < k && i < xsquare.size(); i++) {
			nGram e = xsquare.get(i);
			if (!subNgram.contains(e)) {
				ret.add(e);
				subNgram.add(e);
			}
		}
		return ret;
	}

	/**
	 * call this sieve(), result will store in xsquare
	 */
	public void sieve() {
		viewsMap = new TreeMap<nGram, Integer>();
		otherMap = new TreeMap<nGram, Integer>();
		viewsCount = new TreeMap<nGram, Integer>();
		otherCount = new TreeMap<nGram, Integer>();

		for (int i = 0; i < views.length; i++) {
			ArrayList<nGram> t = ModelUtilities.transformNgram(views[i], n);
			storeNgram(t, 1);
		}
		for (int i = 0; i < otherViews.length; i++) {
			ArrayList<nGram> t = ModelUtilities
					.transformNgram(otherViews[i], n);
			storeNgram(t, -1);
		}
		ngramCount = viewsMap.size();
		xsquare = new ArrayList<nGram>();
		for (Map.Entry<nGram, Integer> entry : viewsMap.entrySet()) {
			double A, B, C, D;
			A = viewsCount.get(entry.getKey());
			if (otherCount.containsKey(entry.getKey()))
				B = otherCount.get(entry.getKey());
			else
				B = 0;
			C = views.length - A;
			D = otherViews.length - B;
			assert (A + C != 0 && B + D != 0 && A + B != 0 && C + D != 0);
			nGram e = entry.getKey();
			double score = entry.getValue() * Math.pow(A * D - C * B, 2)
					/ (A + C) / (B + D) / (A + B) / (C + D);
			e.score = score;
			xsquare.add(e);
		}
		Collections.sort(xsquare, new Comparator<nGram>() {
			@Override
			public int compare(nGram a, nGram b) {
				int c = Double.compare(a.score, b.score);
				if (c == 0) {
					int an = a.getNonTerminal();
					int bn = b.getNonTerminal();
					return Integer.compare(an, bn);
				}
				return -c;
			}
		});
	}
}
