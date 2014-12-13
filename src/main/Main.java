package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import model.Classifier;
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
		String[] posTrain = Arrays
				.copyOfRange(posViews, 0, posViews.length / 2);
		String[] negTrain = Arrays
				.copyOfRange(negViews, 0, negViews.length / 2);
		String[] posTest = Arrays.copyOfRange(posViews, posViews.length / 2,
				posViews.length);
		String[] negTest = Arrays.copyOfRange(negViews, negViews.length / 2,
				negViews.length);
		int Ngram = 5;
		System.out.printf("positive sieve %d-grams building ...\n", Ngram);
		DataSieve posSieve = new DataSieve(Ngram, posTrain, negTrain);

		// posSieve.printBestNgram(100);

		System.out.printf("negative sieve %d-grams building ...\n", Ngram);
		DataSieve negSieve = new DataSieve(Ngram, negTrain, posTrain);

		System.out.printf("positive #ngram = %d\n", posSieve.ngramCount);
		System.out.printf("negative #ngram = %d\n", negSieve.ngramCount);

		int topNgram = 10000;
		ArrayList<nGram> posPick = posSieve.getBestNgram(topNgram);
		ArrayList<nGram> negPick = negSieve.getBestNgram(topNgram);
		ArrayList<nGram> mixPick = mergePick(posPick, negPick, topNgram);

		topNgram = mixPick.size();

		testWinnow(Ngram, topNgram, mixPick, posTest, negTest);
		testPassiveAggressive(Ngram, topNgram, mixPick, posTest, negTest);

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
				ret.add(b.get(j));
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
		int ITLIMIT = 40;
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
		System.out.printf("complete |%40s|\n", "");
		System.out.printf("         |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf("-");
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
		System.out.printf("|\n");
		System.out.printf("Winnow algorithm top-%d testing ...\n", topNgram);
		testClassifier("Winnow", MLmachine, posVec, negVec);
		return;
	}

	public static void testPassiveAggressive(int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posViews, String[] negViews) {
		System.out.printf("Passive-Aggressive algorithm top-%d prepare ...\n",
				topNgram);
		PassiveAggressive PAmachine = new PassiveAggressive(topNgram);
		int ITLIMIT = 40;
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
		System.out.printf("complete |%40s|\n", "");
		System.out.printf("         |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf("-");
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
		System.out.printf("|\n");
		System.out.printf("Passive-Aggressive algorithm top-%d testing ...\n",
				topNgram);
		testClassifier("Passive-Aggressive", PAmachine, posVec, negVec);
		return;
	}

	public static void testClassifier(String algName, Classifier classifier,
			ArrayList<double[]> posVec, ArrayList<double[]> negVec) {
		int[][] tablePos, tableNeg, tableAll;
		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (int i = 0; i < posVec.size(); i++) {
			if (classifier.classify(posVec.get(i))) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
			}
		}
		for (int i = 0; i < negVec.size(); i++) {
			if (classifier.classify(negVec.get(i))) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
			}
		}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		ModelUtilities.printTable(algName + " Class Positive", tablePos);
		ModelUtilities.printTable(algName + " Class Negative", tableNeg);
		ModelUtilities.printTable(algName + " Final", tableAll);
	}
}
