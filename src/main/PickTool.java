package main;

import java.util.ArrayList;
import java.util.TreeSet;

import model.SubGramSet;
import model.nGram;

public class PickTool {
	public ArrayList<nGram> posPick, negPick;
	public TreeSet<nGram> banSet, retainNgram;

	public PickTool(ArrayList<nGram> posPick, ArrayList<nGram> negPick,
			TreeSet<nGram> retainNgram) {
		this.posPick = posPick;
		this.negPick = negPick;
		this.banSet = new TreeSet<nGram>();
		if (retainNgram != null)
			this.retainNgram = retainNgram;
	}

	public ArrayList<nGram> getMixPick(int n, TreeSet<nGram> posPickSet,
			TreeSet<nGram> negPickSet) {
		if (retainNgram != null)
			return getLimitedMixPick(n, posPickSet, negPickSet);
		SubGramSet subNgram = new SubGramSet();
		ArrayList<nGram> ret = new ArrayList<nGram>();
		nGram e = null;
		int i = 0, j = 0;
		while (ret.size() < n && i < posPick.size() && j < negPick.size()) {
			e = null;
			int comp = Double.compare(posPick.get(i).score,
					negPick.get(j).score);
			int from = 0;
			if (comp == 1 || (comp == 0)) {
				e = posPick.get(i);
				i++;
				from = 0;
			} else {
				e = negPick.get(j);
				j++;
				from = 1;
			}
			if (!subNgram.contains(e) && !banSet.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				if (from == 0)
					posPickSet.add(e);
				else
					negPickSet.add(e);
			}
		}

		while (ret.size() < n && i < posPick.size()) {
			e = posPick.get(i);
			if (!subNgram.contains(e) && !banSet.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				posPickSet.add(e);
			}
			i++;
		}

		while (ret.size() < n && j < negPick.size()) {
			e = negPick.get(j);
			if (!subNgram.contains(e) && !banSet.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				negPickSet.add(e);
			}
			j++;
		}
		return ret;
	}

	private ArrayList<nGram> getLimitedMixPick(int n,
			TreeSet<nGram> posPickSet, TreeSet<nGram> negPickSet) {
		SubGramSet subNgram = new SubGramSet();
		ArrayList<nGram> ret = new ArrayList<nGram>();
		nGram e = null;
		int i = 0, j = 0;
		while (ret.size() < n && i < posPick.size() && j < negPick.size()) {
			e = null;
			int comp = Double.compare(posPick.get(i).score,
					negPick.get(j).score);
			int from = 0;
			if (comp == 1 || (comp == 0)) {
				e = posPick.get(i);
				i++;
				from = 0;
			} else {
				e = negPick.get(j);
				j++;
				from = 1;
			}
			if (!subNgram.contains(e) && retainNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				if (from == 0)
					posPickSet.add(e);
				else
					negPickSet.add(e);
			}
		}

		while (ret.size() < n && i < posPick.size()) {
			e = posPick.get(i);
			if (!subNgram.contains(e) && retainNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				posPickSet.add(e);
			}
			i++;
		}

		while (ret.size() < n && j < negPick.size()) {
			e = negPick.get(j);
			if (!subNgram.contains(e) && retainNgram.contains(e)) {
				subNgram.add(e);
				ret.add(e);
				negPickSet.add(e);
			}
			j++;
		}
		return ret;
	}

	public void ban(nGram e) {
		banSet.add(e);
	}
}
