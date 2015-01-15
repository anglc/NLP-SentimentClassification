package model;

import java.util.Arrays;

public class nGram implements Comparable<Object> {
	public static final nGram POS = new nGram(new int[]{-2, -2, -2});
	public static final nGram NEG = new nGram(new int[]{-3, -3, -3});
	public String[] sWord;
	public int[] iWord;
	public double score;

	public nGram() {

	}

	public nGram(int[] i) {
		set(i);
	}

	public nGram(String[] s) {
		set(s);
	}

	public void set(int[] i) {
		iWord = i.clone();
	}

	public void set(String[] s) {
		sWord = s.clone();
	}

	@Override
	public int compareTo(Object other) throws ClassCastException {
		nGram obj = (nGram) other;
		if (iWord != null && obj.iWord != null) {
			for (int i = 0; i < iWord.length; i++) {
				if (iWord[i] != obj.iWord[i])
					return iWord[i] < obj.iWord[i] ? 1 : -1;
			}
			return 0;
		}
		if (sWord != null && obj.sWord != null) {
			for (int i = 0; i < sWord.length; i++) {
				if (!sWord[i].equals(obj.sWord[i]))
					return sWord[i].compareTo(obj.sWord[i]);
			}
			return 0;
		}
		return 0;
	}

	public int getNonTerminal() {
		if (iWord != null) {
			int ret = 0;
			for (int i = 0; i < iWord.length; i++)
				ret += iWord[i] >= 0 ? 1 : 0;
			return ret;
		}
		return 0;
	}

	public void dag() {
		if (iWord != null)
			Arrays.sort(iWord, 0, getNonTerminal());
	}
}
