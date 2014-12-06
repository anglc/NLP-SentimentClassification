package model;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class ModelUtilities {
	private static TreeMap<String, Integer> renameMap = new TreeMap<String, Integer>();
	private static TreeMap<Integer, String> invRenameMap = new TreeMap<Integer, String>();

	public static int rename(String token) {
		if (renameMap.containsKey(token)) {
			return renameMap.get(token);
		} else {
			invRenameMap.put(renameMap.size(), token);
			renameMap.put(token, renameMap.size());
			return renameMap.get(token);
		}
	}

	public static ArrayList<nGram> transformNgram(String s, int n) {
		ArrayList<nGram> ret = new ArrayList<nGram>();
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens())
			tokens.add(st.nextToken());
		int[] iTokens = new int[tokens.size()];
		for (int i = 0; i < tokens.size(); i++)
			iTokens[i] = rename(tokens.get(i));
		for (int i = 0; i < tokens.size(); i++) {
			int[] a = new int[n];
			for (int j = 0; j < n && i + j < tokens.size(); j++)
				a[j] = iTokens[i + j];
			nGram e = new nGram(a);
			ret.add(e);
		}
		return ret;
	}
	
	public static double[] getCharacteristicVector(String s, int n, ArrayList<nGram> vec) {
		ArrayList<nGram> t = transformNgram(s, n);
		TreeSet<nGram> tSet = new TreeSet<nGram>(t);
		double[] ret = new double[vec.size()];
		for (int i = 0; i < vec.size(); i++) {
			if (tSet.contains(vec.get(i)))
				ret[i] = 1;
			else
				ret[i] = 0;
		}
		return ret;
	}
	public static double[] getCharacteristicWeightVector(String s, int n, ArrayList<nGram> vec) {
		ArrayList<nGram> t = transformNgram(s, n);
		TreeMap<nGram, Integer> tMap = new TreeMap<nGram, Integer>();
		for (int i = 0; i < t.size(); i++) {
			int count = 1;
			if (tMap.containsKey(t.get(i)))
				count = tMap.get(t.get(i)) + 1;
			tMap.put(t.get(i), count);
		}
		double[] ret = new double[vec.size()];
		for (int i = 0; i < vec.size(); i++) {
			if (tMap.containsKey(vec.get(i)))
				ret[i] = tMap.get(vec.get(i));
			else
				ret[i] = 0;
		}
		return ret;
	}
	public static String getWordName(int id) {
		if (invRenameMap.containsKey(id))
			return invRenameMap.get(id);
		return "";
	}
}
