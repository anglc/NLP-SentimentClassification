package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import comp.ReturnCell;

public class ModelUtilities {
	private static TreeMap<String, Integer> renameMap = new TreeMap<String, Integer>();
	private static TreeMap<Integer, String> invRenameMap = new TreeMap<Integer, String>();
	private static TreeMap<String, Integer> wordWeight = new TreeMap<String, Integer>();

	public static void addWordWeight(TreeMap<String, Integer> w) {
		for (Map.Entry<String, Integer> entry : w.entrySet()) {
			int ww = 0;
			if (wordWeight.containsKey(entry.getKey()))
				ww = wordWeight.get(entry.getKey());
			wordWeight.put(entry.getKey(),
					Math.abs(ww) + Math.abs(entry.getValue()));
		}
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
		String[] regex = { "\\(", "\\)", "\"", "!", "-", "\\d+", "_", "/", "`",
				"=", "\\]", "\\[", ">|<" };
		for (String e : regex)
			s = s.replaceAll(e, " ");
		s = s.replaceAll("\\+", "'");
		s = s.replaceAll("cannot", " can not");
		s = s.replaceAll("can't", " can not");
		s = s.replaceAll("n't", " not");
		s = s.replaceAll("'re", " are");
		s = s.replaceAll("'s", " is");
		s = s.replaceAll("'m", " am");
		s = s.replaceAll("'d", " would");
		s = s.replaceAll("'ve", " have");
		s = s.replaceAll("'ll", " will");
		s = s.replaceAll("&", " and ");
		s = s.replaceAll("\\+", " ");
		return s;
	}

	public static TreeSet<String> ignoreTokens = new TreeSet<String>();
	public static TreeSet<String> notTokens = new TreeSet<String>();
	public static TreeSet<String> posTokens = new TreeSet<String>();
	public static TreeSet<String> negTokens = new TreeSet<String>();

	public static boolean sieveToken(String s) {
		if (ignoreTokens.contains(s))
			return false;
		return true;
	}

	public static String removeDuplicateCharToken(String s) {
		if (s.length() == 0)
			return s;
		String ret = "" + s.charAt(0);
		char prev = s.charAt(0);
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == prev)
				continue;
			ret += s.charAt(i);
			prev = s.charAt(i);
		}
		return ret;
	}

	public static String replaceSynonym(String s) {
		s = s.replaceAll("'", "");
		if (s.equals("are") || s.equals("is") || s.equals("was")
				|| s.equals("were") || s.equals("am"))
			return "be";
		if (s.equals("film"))
			return "movie";
		// if (s.equals("no") || s.equals("never") || s.equals("but")||
		// s.equals("neither"))
		if (notTokens.contains(s))
			return "not";
		if (s.equals("u"))
			return "you";
		if (s.equals("him"))
			return "her";
		if (s.equals("he"))
			return "she";
		if (s.equals("him"))
			return "her";
		if (s.length() == 1)
			return "";
		if (renameMap.containsKey(removeDuplicateCharToken(s)))
			return removeDuplicateCharToken(s);
		return s;
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
				// e.dag();
				ret.add(e);
			}
		}
		return ret;
	}

	public static ArrayList<String> transformTokens(String s) {
		ArrayList<String> ret = new ArrayList<String>();
		String[] stmt = s.split("\\.|,|:|;|\\?|!|\\*");
		for (String ss : stmt) {
			ss = sieveString(ss);
			StringTokenizer st = new StringTokenizer(ss);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				token = token.toLowerCase();
				token = replaceSynonym(token);
				if (sieveToken(token) && token.length() > 0)
					ret.add(token);
			}
		}
		return ret;
	}

	public static ArrayList<nGram> transformNgram(String s, int n,
			ReturnCell<Integer> sentCount, ReturnCell<Integer> tokenCount) {
		ArrayList<nGram> ret = new ArrayList<nGram>();
		String[] stmt = s.split("\\.|,|:|;|\\?|!|\\*");
		int scount = 0, tcount = 0;
		for (String ss : stmt) {
			scount++;
			ss = sieveString(ss);
			ArrayList<String> tokens = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(ss);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				token = token.toLowerCase();
				token = replaceSynonym(token);
				if (sieveToken(token) && token.length() > 0)
					tokens.add(token);
				else {

				}
			}
			if (tokens.size() > 0) {
				ret.addAll(transfromNgramPhrase(tokens, n));
				tcount += tokens.size();
			}
		}
		if (sentCount != null)
			sentCount.set(scount);
		if (tokenCount != null)
			tokenCount.set(tcount);
		return ret;
	}

	public static ArrayList<nGram> transformNgram(String s, int n) {
		return transformNgram(s, n, null, null);
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

	public static TreeMap<Integer, Double> getCharacteristicWeightVector(
			String s, int n, TreeMap<nGram, Integer> mixPickPosMap,
			ReturnCell<Integer> sentCount, ReturnCell<Integer> tokenCount) {
		ArrayList<nGram> t = transformNgram(s, n, sentCount, tokenCount);
		ArrayList<String> tokens = transformTokens(s);
		TreeMap<nGram, Integer> tMap = new TreeMap<nGram, Integer>();
		TreeMap<nGram, Integer> tCountMap = new TreeMap<nGram, Integer>();
		TreeMap<Integer, Double> vec = new TreeMap<Integer, Double>();
		int pos = 0, neg = 0;
		for (nGram e : t) {
			int count = scoreNgram(e);
			tMap.put(e, count);
			count = 1;
			if (tCountMap.containsKey(e))
				count = tCountMap.get(e) + 1;
			tCountMap.put(e, count);
		}

		for (Map.Entry<nGram, Integer> e : tMap.entrySet()) {
			if (mixPickPosMap.containsKey(e.getKey())) {
				double v = e.getValue();
				vec.put(mixPickPosMap.get(e.getKey()),
						v + Math.sqrt(tCountMap.get(e.getKey())));
			}
		}

		for (String token : tokens) {
			if (posTokens.contains(token))
				pos++;
			if (negTokens.contains(token))
				neg++;
		}

		vec.put(mixPickPosMap.get(nGram.POS), (double) pos);
		vec.put(mixPickPosMap.get(nGram.NEG), (double) neg);

		return vec;
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
				ret *= Math.abs(wordWeight.get(w));
		}
		return ret;
	}
}