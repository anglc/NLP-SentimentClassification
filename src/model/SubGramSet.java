package model;

import java.util.TreeSet;

public class SubGramSet {
	TreeSet<nGram> subNgram;

	public SubGramSet() {
		subNgram = new TreeSet<nGram>();
	}

	public boolean contains(nGram e) {
		if (e == null)
			return true;
		return subNgram.contains(e);
	}

	/**
	 * (1, 2, 3, -1) -> (1, -1, -1, -1)-> (1, 2, -1, -1) ... -> (2, -1, -1, -1)
	 * -> (2, 3, -1, -1) ...
	 * 
	 * @param e
	 */
	public void add(nGram e) {
		for (int p = 0; p < e.iWord.length; p++) {
			if (e.iWord[p] == -1)
				break;
			int[] sub = new int[e.iWord.length];
			for (int q = 0; q < sub.length; q++)
				sub[q] = -1;
			for (int q = p, t = 0; q < e.iWord.length; q++, t++) {
				sub[t] = e.iWord[q];
				if (e.iWord[q] == -1)
					break;
				subNgram.add(new nGram(sub));
			}
		}
	}
}
