package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import model.Classifier;
import model.DataSieve;
import model.LanguageModel;
import model.ModelUtilities;
import model.PassiveAggressive;
import model.WinnowMachine;
import model.nGram;

public class Main {
	public static void main(String[] args) {
		Loader loader = new Loader("training_set");
		ModelUtilities.addWordWeight(loader.wordWeight);
		// Loader loader = new Loader("testcase");
		String[] posViews = (String[]) loader.posViews.toArray(new String[0]);
		String[] negViews = (String[]) loader.negViews.toArray(new String[0]);
		String[] posTrain = Arrays.copyOfRange(posViews, 0,
				posViews.length * 2 / 4);
		String[] negTrain = Arrays.copyOfRange(negViews, 0,
				negViews.length * 2 / 4);
		String[] posTest = Arrays.copyOfRange(posViews,
				posViews.length * 2 / 4, posViews.length);
		String[] negTest = Arrays.copyOfRange(negViews,
				negViews.length * 2 / 4, negViews.length);
		int Ngram = 2;
		int topNgram = 10000;

		experiment(Ngram, topNgram, posTest, negTest, posTrain, negTrain);
		experiment(Ngram, topNgram, posTrain, negTrain, posTest, negTest);
	}

	public static void experiment(int Ngram, int topNgram, String[] posTrain,
			String[] negTrain, String[] posTest, String[] negTest) {
		System.out.printf("positive sieve %d-grams building ...\n", Ngram);
		DataSieve posSieve = new DataSieve(Ngram, posTrain, negTrain);

		// posSieve.printBestNgram(100);

		System.out.printf("negative sieve %d-grams building ...\n", Ngram);
		DataSieve negSieve = new DataSieve(Ngram, negTrain, posTrain);

		System.out.printf("positive #ngram = %d\n", posSieve.ngramCount);
		System.out.printf("negative #ngram = %d\n", negSieve.ngramCount);

		ArrayList<nGram> posPick = posSieve.getBestNgram(topNgram);
		ArrayList<nGram> negPick = negSieve.getBestNgram(topNgram);
		ArrayList<nGram> mixPick = mergePick(posPick, negPick, topNgram);

		topNgram = mixPick.size();

		LanguageModel LMmachine;
		WinnowMachine MLmachine;
		PassiveAggressive PAmachine;
		LMmachine = testLanguageModel(Ngram, topNgram, mixPick, posTrain,
				negTrain, posTest, negTest);
		MLmachine = testWinnow(Ngram, topNgram, mixPick, posTrain, negTrain,
				posTest, negTest);
		PAmachine = testPassiveAggressive(Ngram, topNgram, mixPick, posTrain,
				negTrain, posTest, negTest);
		testThreeWaysInterview(LMmachine, MLmachine, PAmachine, Ngram,
				topNgram, mixPick, posTest, negTest);
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

	public static void testThreeWaysInterview(LanguageModel LMmachine,
			WinnowMachine MLmachine, PassiveAggressive PAmachine, int Ngram,
			int topNgram, ArrayList<nGram> mixPick, String[] posTest,
			String[] negTest) {
		System.out.printf("Three-Ways Online top-%d prepare ...\n", topNgram);
		ArrayList<double[]> posVec2 = new ArrayList<double[]>();
		ArrayList<double[]> negVec2 = new ArrayList<double[]>();
		for (String pos : posTest) {
			double[] vec = ModelUtilities.getCharacteristicVector(pos, Ngram,
					mixPick);
			posVec2.add(vec);
		}
		for (String neg : negTest) {
			double[] vec = ModelUtilities.getCharacteristicVector(neg, Ngram,
					mixPick);
			negVec2.add(vec);
		}
		System.out.printf("Three-Ways Online top-%d testing ...\n", topNgram);
		int ONLINE_TRAIN = 1;
		int posIdx = 0, negIdx = 0;
		int ac = 0, wa = 0;
		int t1 = 0, t2 = 0, t3 = 0, t4 = 0;
		while (posIdx < posTest.length || negIdx < negTest.length) {
			boolean[] predict = new boolean[3];
			boolean correct = false, meeting = false;
			String views = null;
			double[] viewsVec = null;
			if ((Math.random() < 0.5 && posIdx < posTest.length)
					|| (negIdx >= negTest.length)) {
				views = posTest[posIdx];
				viewsVec = posVec2.get(posIdx);
				posIdx++;
				correct = true;
			} else if (negIdx < negTest.length) {
				views = negTest[negIdx];
				viewsVec = negVec2.get(negIdx);
				negIdx++;
				correct = false;
			}
			for (int discuss = 0; discuss < 1; discuss++) {
				predict[0] = LMmachine.classify(views, mixPick);
				predict[1] = MLmachine.classify(viewsVec);
				predict[2] = PAmachine.classify(viewsVec);
				int pos = 0, neg = 0;
				for (int i = 0; i < 3; i++) {
					if (predict[i] == true)
						pos++;
					else
						neg++;
				}
				if (pos > neg)
					meeting = true;
				else
					meeting = false;
				if (pos == 3 || neg == 3) {
					if (meeting != correct)
						t1++;
					else
						t2++;
					break;
				}
				meeting = predict[2];
				if (predict[1] == predict[0])
					meeting = predict[1];
//				if (predict[2] == predict[0])
//					meeting = predict[0];
				if (meeting != correct)
					t3++;
				else
					t4++;
				break;
				// for (int i = 0; i < 3; i++) {
				// if (predict[i] != meeting) {
				// switch (i) {
				// case 0:
				// LMmachine.add(views, meeting ? "pos" : "neg",
				// mixPick);
				// break;
				// case 1:
				// for (int it = 0; it < ONLINE_TRAIN; it++)
				// MLmachine.add(viewsVec, meeting ? 1 : 0);
				// break;
				// case 2:
				// for (int it = 0; it < ONLINE_TRAIN; it++)
				// PAmachine.add(viewsVec, meeting ? 1 : -1);
				// break;
				// }
				// }
				// }
			}
			if (meeting == correct)
				ac++;
			else
				wa++;
		}
		System.out.printf("%d / %d (AC/WA)\n", ac, wa);
		System.out.printf("P = %f %%\n", ac * 100.0 / (ac + wa));
		System.out.printf("1-oponin %d / %d (AC/WA)\n", t2, t1);
		System.out.printf("n-oponin %d / %d (AC/WA)\n", t4, t3);
		return;
	}

	public static LanguageModel testLanguageModel(int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posViews, String[] negViews,
			String[] posTest, String[] negTest) {
		System.out.printf("Language Model top-%d prepare ...\n", topNgram);
		LanguageModel LMmachine = new LanguageModel(Ngram);
		for (String pos : posViews) {
			LMmachine.add(pos, "pos", mixPick);
		}
		for (String neg : negViews)
			LMmachine.add(neg, "neg", mixPick);
		System.out.printf("Language Model top-%d testing ...\n", topNgram);
		int[][] tablePos, tableNeg, tableAll;
		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (String pos : posTest) {
			if (LMmachine.classify(pos, mixPick)) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
			}
		}
		for (String neg : negTest) {
			if (LMmachine.classify(neg, mixPick)) {
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

		String algName = "Language Model";
		ModelUtilities.printTable(algName + " Class Positive", tablePos);
		ModelUtilities.printTable(algName + " Class Negative", tableNeg);
		ModelUtilities.printTable(algName + " Final", tableAll);
		return LMmachine;
	}

	public static WinnowMachine testWinnow(int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posViews, String[] negViews,
			String[] posTest, String[] negTest) {
		System.out.printf("Winnow algorithm top-%d prepare ...\n", topNgram);
		WinnowMachine MLmachine = new WinnowMachine(topNgram);
		int ITLIMIT = 40;
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		ArrayList<double[]> posVec2 = new ArrayList<double[]>();
		ArrayList<double[]> negVec2 = new ArrayList<double[]>();
		for (String pos : posViews) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos, Ngram,
					mixPick);
			posVec.add(vec);
		}
		for (String neg : negViews) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg, Ngram,
					mixPick);
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
		for (String pos : posTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos, Ngram,
					mixPick);
			posVec2.add(vec);
		}
		for (String neg : negTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg, Ngram,
					mixPick);
			negVec2.add(vec);
		}
		testClassifier("Winnow", MLmachine, posVec2, negVec2);
		return MLmachine;
	}

	public static PassiveAggressive testPassiveAggressive(int Ngram,
			int topNgram, ArrayList<nGram> mixPick, String[] posViews,
			String[] negViews, String[] posTest, String[] negTest) {
		System.out.printf("Passive-Aggressive algorithm top-%d prepare ...\n",
				topNgram);
		PassiveAggressive PAmachine = new PassiveAggressive(topNgram);
		int ITLIMIT = 40;
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		ArrayList<double[]> posVec2 = new ArrayList<double[]>();
		ArrayList<double[]> negVec2 = new ArrayList<double[]>();
		for (String pos : posViews) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos, Ngram,
					mixPick);
			posVec.add(vec);
		}
		for (String neg : negViews) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg, Ngram,
					mixPick);
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
		for (String pos : posTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos, Ngram,
					mixPick);
			posVec2.add(vec);
		}
		for (String neg : negTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg, Ngram,
					mixPick);
			negVec2.add(vec);
		}
		testClassifier("Passive-Aggressive", PAmachine, posVec2, negVec2);
		return PAmachine;
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
