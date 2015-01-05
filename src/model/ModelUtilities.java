package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class ModelUtilities {
	private static TreeMap<String, Integer> renameMap = new TreeMap<String, Integer>();
	private static TreeMap<Integer, String> invRenameMap = new TreeMap<Integer, String>();
	private static TreeMap<String, Integer> wordWeight = new TreeMap<String, Integer>();

	public static void addWordWeight(TreeMap<String, Integer> w) {
		for (Map.Entry<String, Integer> entry : w.entrySet())
			wordWeight.put(entry.getKey(), entry.getValue());
	}

	public static int rename(String token) {
		if (renameMap.containsKey(token)) {
			return renameMap.get(token);
		} else {
			invRenameMap.put(renameMap.size(), token);
			renameMap.put(token, renameMap.size());
			return renameMap.get(token);
		}
	}

	public static String sieveString(String s) {
		String[] regex = { "\\(", "\\)", "\"", "!" };
		for (String e : regex)
			s = s.replaceAll(e, "");
		return s;
	}

	public static String[] ignoreToken = { "the", "are", "is", "i", "it", "he",
			"she", "-", "a", "an" };

	public static boolean sieveToken(String s) {
		for (String e : ignoreToken)
			if (s.equals(e))
				return false;
		return true;
	}

	public static ArrayList<nGram> transformNgram(String s, int n) {
		ArrayList<nGram> ret = new ArrayList<nGram>();
		String[] stmt = s.split("\\.|,|:|;|\\?|!");
		for (String ss : stmt) {
			ss = sieveString(ss);
			ArrayList<String> tokens = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(ss);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (sieveToken(token))
					tokens.add(token);
			}
			int[] iTokens = new int[tokens.size()];
			for (int i = 0; i < tokens.size(); i++)
				iTokens[i] = rename(tokens.get(i));
			for (int k = 1; k <= n; k++) {
				for (int i = 0; i < tokens.size(); i++) {
					int[] a = new int[n];
					for (int j = 0; j < n; j++)
						a[j] = -1;
					for (int j = 0; j < k && i + j < tokens.size(); j++)
						a[j] = iTokens[i + j];
					nGram e = new nGram(a);
					ret.add(e);
				}
			}
		}
		return ret;
	}

	public static double[] getCharacteristicVector(String s, int n,
			ArrayList<nGram> vec) {
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

	public static double[] getCharacteristicWeightVector(String s, int n,
			ArrayList<nGram> vec) {
		ArrayList<nGram> t = transformNgram(s, n);
		TreeMap<nGram, Integer> tMap = new TreeMap<nGram, Integer>();
		for (nGram e : t) {
			int count = scoreNgram(e);
			tMap.put(e, count);
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

	public static int scoreNgram(nGram e) {
		int ret = 1;
		for (int i = 0; i < e.iWord.length; i++) {
			String w = getWordName(e.iWord[i]);
			if (wordWeight.containsKey(w))
				ret += Math.abs(wordWeight.get(w));
		}
		return ret;
	}

	public static void printTable(String tableName, int table[][]) {
		System.out.printf("Table `%s`\n\n", tableName);
		System.out.printf("|%16s|%15s|%15s|\n", "Truth\\Classifier",
				"Classifier no", "Classifier yes");
		System.out.printf("|%16s|%15s|%15s|\n", "----------------",
				"---------------", "---------------");
		System.out.printf("|%16s|%15d|%15d|\n", "Truth no", table[0][0],
				table[0][1]);
		System.out.printf("|%16s|%15d|%15d|\n", "Truth yes", table[1][0],
				table[1][1]);

		double P, R, F1, beta = 1;
		P = (double) table[1][1] / (table[1][0] + table[1][1]);
		R = (double) table[1][1] / (table[0][1] + table[1][1]);
		F1 = (beta * beta + 1) * P * R / (beta * beta * P + R);
		System.out.printf("\nP  %f %%, R  %f %%, F1  %f %%\n", P, R, F1);
		System.out.println();
	}

	public static ArrayList<nGram> mergePick(ArrayList<nGram> a,
			ArrayList<nGram> b, int n) {
		SubGramSet subNgram = new SubGramSet();
		ArrayList<nGram> ret = new ArrayList<nGram>();
		nGram e = null;
		int i = 0, j = 0;
		while (ret.size() < n && i < a.size() && j < b.size()) {
			e = null;
			int comp = Double.compare(a.get(i).score, b.get(j).score);
			if (comp == 1 || (comp == 0 && Math.random() > 0.5)) {
				e = a.get(i);
				i++;
			} else {
				e = b.get(j);
				j++;
			}
			if (!subNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
			}
		}

		while (ret.size() < n && i < a.size()) {
			e = a.get(i);
			if (!subNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
			}
			i++;
		}

		while (ret.size() < n && j < b.size()) {
			e = b.get(j);
			if (!subNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
			}
			j++;
		}
		return ret;
	}
}
