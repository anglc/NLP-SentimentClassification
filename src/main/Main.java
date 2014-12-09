package main;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import model.DataSieve;
import model.ModelUtilities;
import model.PassiveAggressive;
import model.WinnowMachine;
import model.nGram;

public class Main {
	public static void main(String[] args) {
		Loader loader = new Loader("training_set");
		// Loader loader = new Loader("testcase");
		String[] posViews = (String[]) loader.posViews.toArray(new String[0]);
		String[] negViews = (String[]) loader.negViews.toArray(new String[0]);

		int Ngram = 3;
		System.out.printf("positive sieve %d-grams building ...\n", Ngram);
		DataSieve posSieve = new DataSieve(Ngram, posViews, negViews);
		System.out.printf("negative sieve %d-grams building ...\n", Ngram);
		DataSieve negSieve = new DataSieve(Ngram, negViews, posViews);

		// posSieve.printBestNgram(10);

		int topNgram = 1000;
		ArrayList<nGram> posPick = posSieve.getBestNgram(topNgram);
		ArrayList<nGram> negPick = negSieve.getBestNgram(topNgram);
		ArrayList<nGram> mixPick = mergePick(posPick, negPick, topNgram);

		topNgram = mixPick.size();

		testWinnow(Ngram, topNgram, mixPick, posViews, negViews);
		testPassiveAggressive(Ngram, topNgram, mixPick, posViews, negViews);

	}

	public static ArrayList<nGram> mergePick(ArrayList<nGram> a,
			ArrayList<nGram> b, int n) {
		ArrayList<nGram> ret = new ArrayList<nGram>();
		int i = 0, j = 0;
		while (ret.size() < n && i < a.size() && j < b.size()) {
			if (a.get(i).score > b.get(j).score) {
				ret.add(a.get(i));
				i++;
			} else {
				ret.add(a.get(j));
				j++;
			}
		}

		while (ret.size() < n && i < a.size()) {
			ret.add(a.get(i));
			i++;
		}

		while (ret.size() < n && j < b.size()) {
			ret.add(b.get(j));
			j++;
		}
		return ret;
	}

	public static void testWinnow(int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posViews, String[] negViews) {
		System.out.printf("Winnow algorithm top-%d prepare ...\n", topNgram);
		WinnowMachine MLmachine = new WinnowMachine(topNgram);
		int ac = 0, wa = 0, ITLIMIT = 20;
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		for (int i = 0; i < posViews.length; i++) {
			double[] vec = ModelUtilities.getCharacteristicVector(posViews[i],
					Ngram, mixPick);
			posVec.add(vec);
		}
		for (int i = 0; i < negViews.length; i++) {
			double[] vec = ModelUtilities.getCharacteristicVector(negViews[i],
					Ngram, mixPick);
			negVec.add(vec);
		}
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf("complete %.0f%%\n", (double) it / ITLIMIT
					* 100.0);
			int posIdx = 0, negIdx = 0;
			while (posIdx < posViews.length || negIdx < negViews.length) {
				if (Math.random() < 0.5 && posIdx < posViews.length) {
					MLmachine.add(posVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < negViews.length) {
					MLmachine.add(negVec.get(negIdx), 0);
					negIdx++;
				}
			}
		}
		System.out.printf("Winnow algorithm top-%d testing ...\n", topNgram);
		for (int i = 0; i < posViews.length; i++) {
			if (MLmachine.classify(posVec.get(i)))
				ac++;
			else
				wa++;
		}
		for (int i = 0; i < negViews.length; i++) {
			if (MLmachine.classify(negVec.get(i)))
				wa++;
			else
				ac++;
		}
		System.out.printf("Winnow result %d / %d (AC/WA)\n", ac, wa);
		return;
	}

	public static void testPassiveAggressive(int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posViews, String[] negViews) {
		System.out.printf("Passive-Aggressive algorithm top-%d prepare ...\n",
				topNgram);
		PassiveAggressive PAmachine = new PassiveAggressive(topNgram);
		int ac = 0, wa = 0, ITLIMIT = 20;
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		for (int i = 0; i < posViews.length; i++) {
			double[] vec = ModelUtilities.getCharacteristicVector(posViews[i],
					Ngram, mixPick);
			posVec.add(vec);
		}
		for (int i = 0; i < negViews.length; i++) {
			double[] vec = ModelUtilities.getCharacteristicVector(negViews[i],
					Ngram, mixPick);
			negVec.add(vec);
		}
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf("complete %.0f%%\n", (double) it / ITLIMIT
					* 100.0);
			int posIdx = 0, negIdx = 0;
			while (posIdx < posViews.length || negIdx < negViews.length) {
				if (Math.random() < 0.5 && posIdx < posViews.length) {
					PAmachine.add(posVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < negViews.length) {
					PAmachine.add(negVec.get(negIdx), -1);
					negIdx++;
				}
			}
		}

		System.out.printf("Passive-Aggressive algorithm top-%d testing ...\n",
				topNgram);
		ac = 0;
		wa = 0;
		for (int i = 0; i < posViews.length; i++) {
			if (PAmachine.classify(posVec.get(i)) == 1)
				ac++;
			else
				wa++;
		}

		for (int i = 0; i < negViews.length; i++) {
			if (PAmachine.classify(negVec.get(i)) == 1)
				wa++;
			else
				ac++;
		}
		System.out
				.printf("Passive-Aggressive result %d / %d (AC/WA)\n", ac, wa);
		return;
	}
}
