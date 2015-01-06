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
		s = s.replaceAll("n't", " not");
		return s;
	}

	public static TreeSet<String> ignoreToken = new TreeSet<String>();

	public static boolean sieveToken(String s) {
		if (ignoreToken.contains(s))
			return false;
		return true;
	}

	public static ArrayList<nGram> transfromNgramPhrase(
			ArrayList<String> tokens, int n) {
		ArrayList<nGram> ret = new ArrayList<nGram>();
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
				e.dag();
				ret.add(e);
			}
		}
		return ret;
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
				token = token.toLowerCase();
				if (sieveToken(token))
					tokens.add(token);
				else {
					if (tokens.size() > 0) {
						ret.addAll(transfromNgramPhrase(tokens, n));
						tokens.clear();
					}
				}
			}
			if (tokens.size() > 0)
				ret.addAll(transfromNgramPhrase(tokens, n));
		}
		return ret;
	}

	public static TreeMap<nGram, Integer> getNgramOcc(String s, int n,
			TreeMap<nGram, Integer> mixPickPosMap) {
		TreeMap<nGram, Integer> ret = new TreeMap<nGram, Integer>();
		ArrayList<nGram> t = transformNgram(s, n);
		for (nGram e : t) {
			if (mixPickPosMap.containsKey(e)) {
				int count = 1;
				if (ret.containsKey(e))
					count = ret.get(e) + 1;
				ret.put(e, count);
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

	public static TreeMap<Integer, Double> getCharacteristicWeightVector(
			String s, int n, TreeMap<nGram, Integer> mixPickPosMap) {
		ArrayList<nGram> t = transformNgram(s, n);
		TreeMap<nGram, Integer> tMap = new TreeMap<nGram, Integer>();
		for (nGram e : t) {
			int count = scoreNgram(e);
			tMap.put(e, count);
		}
		TreeMap<Integer, Double> ret = new TreeMap<Integer, Double>();
		for (Map.Entry<nGram, Integer> e : tMap.entrySet()) {
			if (mixPickPosMap.containsKey(e.getKey())) {
				double v = e.getValue();
				ret.put(mixPickPosMap.get(e.getKey()), v);
			}
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
		System.out.printf("\nP  %.2f %%, R  %.2f %%, F1  %.2f %%\n", P * 100,
				R * 100, F1 * 100);
		System.out.println();
	}

	public static ArrayList<nGram> mergePick(ArrayList<nGram> a,
			ArrayList<nGram> b, int n, TreeSet<nGram> posPickSet,
			TreeSet<nGram> negPickSet) {
		SubGramSet subNgram = new SubGramSet();
		ArrayList<nGram> ret = new ArrayList<nGram>();
		nGram e = null;
		int i = 0, j = 0;
		while (ret.size() < n && i < a.size() && j < b.size()) {
			e = null;
			int comp = Double.compare(a.get(i).score, b.get(j).score), from = 0;
			if (comp == 1 || (comp == 0 && Math.random() > 0.5)) {
				e = a.get(i);
				i++;
				from = 0;
			} else {
				e = b.get(j);
				j++;
				from = 1;
			}
			if (!subNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				if (from == 0)
					posPickSet.add(e);
				else
					negPickSet.add(e);
			}
		}

		while (ret.size() < n && i < a.size()) {
			e = a.get(i);
			if (!subNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				posPickSet.add(e);
			}
			i++;
		}

		while (ret.size() < n && j < b.size()) {
			e = b.get(j);
			if (!subNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				negPickSet.add(e);
			}
			j++;
		}
		return ret;
	}
}