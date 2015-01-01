package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import UI.Dashboard;
import model.Classifier;
import model.DataSieve;
import model.LanguageModel;
import model.ModelUtilities;
import model.PassiveAggressive;
import model.SubGramSet;
import model.WinnowMachine;
import model.nGram;

public class Main {
	public static void main(String[] args) {
//		Dashboard demo = new Dashboard();
		work(5, 10000, "training_set", 3, 1);
	}

	public static void work(int Ngram, int topNgram, String trainingPath,
			int trainingRatio, int testingRatio) {
		System.out.printf("Ngram %d topNgram %d\n", Ngram, topNgram);
		Loader loader = new Loader(trainingPath);
		ShuffleChooser shuffleChooser = new ShuffleChooser(loader);
		ModelUtilities.addWordWeight(loader.wordWeight);
		String[] posTest, posTrain, negTest, negTrain;

		shuffleChooser.shuffle(trainingRatio, testingRatio);
		posTest = shuffleChooser.posTest
				.toArray(new String[shuffleChooser.posTest.size()]);
		posTrain = shuffleChooser.posTrain
				.toArray(new String[shuffleChooser.posTrain.size()]);
		negTest = shuffleChooser.negTest
				.toArray(new String[shuffleChooser.negTest.size()]);
		negTrain = shuffleChooser.negTrain
				.toArray(new String[shuffleChooser.negTrain.size()]);

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
		PassiveAggressive PAmachine, meetingMachine;
		LMmachine = testLanguageModel(Ngram, topNgram, mixPick, posTrain,
				negTrain, posTest, negTest);
		MLmachine = testWinnow(Ngram, topNgram, mixPick, posTrain, negTrain,
				posTest, negTest);
		PAmachine = testPassiveAggressive(Ngram, topNgram, mixPick, posTrain,
				negTrain, posTest, negTest);
		meetingMachine = prepareMeeting(LMmachine, MLmachine, PAmachine, Ngram,
				topNgram, mixPick, posTrain, negTrain);
		testMeetingInterview(meetingMachine, LMmachine, MLmachine, PAmachine,
				Ngram, topNgram, mixPick, posTest, negTest);
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

	public static PassiveAggressive prepareMeeting(LanguageModel LMmachine,
			WinnowMachine MLmachine, PassiveAggressive PAmachine, int Ngram,
			int topNgram, ArrayList<nGram> mixPick, String[] posTrain,
			String[] negTrain) {
		System.out.printf("Prepare Meeting Machine ...\n");
		PassiveAggressive meetingMachine = new PassiveAggressive(6);
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		ArrayList<double[]> votePosVec = new ArrayList<double[]>();
		ArrayList<double[]> voteNegVec = new ArrayList<double[]>();
		for (String pos : posTrain) {
			double[] vec = ModelUtilities.getCharacteristicVector(pos, Ngram,
					mixPick);
			posVec.add(vec);
		}
		for (String neg : negTrain) {
			double[] vec = ModelUtilities.getCharacteristicVector(neg, Ngram,
					mixPick);
			negVec.add(vec);
		}

		boolean[] predict = new boolean[3];
		double[] predictWeight = new double[3];

		for (int i = 0; i < posVec.size(); i++) {
			String views = null;
			double[] viewsVec = null;
			double[] voteArr = new double[6];
			views = posTrain[i];
			viewsVec = posVec.get(i);

			predict[0] = LMmachine.classify(views, mixPick);
			predict[1] = MLmachine.classify(viewsVec);
			predict[2] = PAmachine.classify(viewsVec);

			predictWeight[0] = LMmachine.strongClassify(views, mixPick);
			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);

			for (int j = 0; j < 3; j++) {
				if (predict[j])
					voteArr[j] = predictWeight[j];
				else
					voteArr[j + 3] = -predictWeight[j];
			}
			votePosVec.add(voteArr);
		}

		for (int i = 0; i < negVec.size(); i++) {
			String views = null;
			double[] viewsVec = null;
			double[] voteArr = new double[6];
			views = negTrain[i];
			viewsVec = negVec.get(i);

			predict[0] = LMmachine.classify(views, mixPick);
			predict[1] = MLmachine.classify(viewsVec);
			predict[2] = PAmachine.classify(viewsVec);

			predictWeight[0] = LMmachine.strongClassify(views, mixPick);
			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);

			for (int j = 0; j < 3; j++) {
				if (predict[j])
					voteArr[j] = predictWeight[j];
				else
					voteArr[j + 3] = -predictWeight[j];
			}
			voteNegVec.add(voteArr);
		}

		int ITLIMIT = 40;
		System.out.printf("complete |%40s|\n", "");
		System.out.printf("         |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf("-");
			int posIdx = 0, negIdx = 0;
			while (posIdx < votePosVec.size() || negIdx < voteNegVec.size()) {
				if (Math.random() < 0.5 && posIdx < votePosVec.size()) {
					meetingMachine.add(votePosVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < voteNegVec.size()) {
					meetingMachine.add(voteNegVec.get(negIdx), -1);
					negIdx++;
				}
			}
		}
		System.out.printf("|\n");

		return meetingMachine;
	}

	public static void testMeetingInterview(PassiveAggressive meetingMachine,
			LanguageModel LMmachine, WinnowMachine MLmachine,
			PassiveAggressive PAmachine, int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posTest, String[] negTest) {
		System.out.printf("Meeting prepare ...\n");
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
		System.out.printf("Meeting top-%d testing ...\n", topNgram);

		int posIdx = 0, negIdx = 0;
		int ac = 0, wa = 0;
		boolean[] predict = new boolean[3];
		double[] predictWeight = new double[3];

		predictWeight[0] = 1;
		predictWeight[1] = 1;
		predictWeight[2] = 1;

		while (posIdx < posTest.length || negIdx < negTest.length) {
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

			predict[0] = LMmachine.classify(views, mixPick);
			predict[1] = MLmachine.classify(viewsVec);
			predict[2] = PAmachine.classify(viewsVec);

			predictWeight[0] = LMmachine.strongClassify(views, mixPick);
			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);

			double[] voteArr = new double[6];
			for (int j = 0; j < 3; j++) {
				if (predict[j])
					voteArr[j] = predictWeight[j];
				else
					voteArr[j + 3] = -predictWeight[j];
			}

			meeting = meetingMachine.classify(voteArr);
			if (meeting == correct)
				ac++;
			else
				wa++;
		}
		System.out.printf("%d / %d (AC/WA)\n", ac, wa);
		System.out.printf("P = %f %%\n", ac * 100.0 / (ac + wa));

		return;
	}

	public static LanguageModel testLanguageModel(int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posViews, String[] negViews,
			String[] posTest, String[] negTest) {
		System.out.printf("Language Model prepare ...\n");
		LanguageModel LMmachine = new LanguageModel(Ngram);
		for (String pos : posViews)
			LMmachine.add(pos, "pos", mixPick);
		for (String neg : negViews)
			LMmachine.add(neg, "neg", mixPick);

		System.out.printf("Language Model self-testing ...\n");

		for (String pos : posViews)
			LMmachine.selfTraining(pos, "pos", mixPick);
		for (String neg : negViews)
			LMmachine.selfTraining(neg, "neg", mixPick);

		System.out.printf("Language Model testing ...\n");
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
		System.out.printf("Winnow algorithm prepare ...\n");
		WinnowMachine MLmachine = new WinnowMachine(topNgram);
		int ITLIMIT = 40;
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		ArrayList<double[]> posVec2 = new ArrayList<double[]>();
		ArrayList<double[]> negVec2 = new ArrayList<double[]>();
		for (String pos : posViews) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos,
					Ngram, mixPick);
			posVec.add(vec);
		}
		for (String neg : negViews) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg,
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

		System.out.printf("Winnow algorithm self-testing ...\n");
		int posIdx = 0, negIdx = 0;
		while (posIdx < posViews.length || negIdx < negViews.length) {
			if (Math.random() < 0.5 && posIdx < posViews.length) {
				MLmachine.selfTraining(posVec.get(posIdx), 1);
				posIdx++;
			} else if (negIdx < negViews.length) {
				MLmachine.selfTraining(negVec.get(negIdx), 0);
				negIdx++;
			}
		}
		// System.out.printf("%f %f\n", MLmachine.selfMin, MLmachine.selfMax);

		System.out.printf("Winnow algorithm testing ...\n");
		for (String pos : posTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos,
					Ngram, mixPick);
			posVec2.add(vec);
		}
		for (String neg : negTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg,
					Ngram, mixPick);
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
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos,
					Ngram, mixPick);
			posVec.add(vec);
		}
		for (String neg : negViews) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg,
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
		System.out.printf("Passive-Aggressive algorithm self-testing ...\n");

		int posIdx = 0, negIdx = 0;
		while (posIdx < posViews.length || negIdx < negViews.length) {
			if (Math.random() < 0.5 && posIdx < posViews.length) {
				PAmachine.selfTraining(posVec.get(posIdx), 1);
				posIdx++;
			} else if (negIdx < negViews.length) {
				PAmachine.selfTraining(negVec.get(negIdx), -1);
				negIdx++;
			}
		}
		// System.out.printf("%f %f\n", PAmachine.selfMin, PAmachine.selfMax);

		System.out.printf("Passive-Aggressive algorithm testing ...\n");
		for (String pos : posTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos,
					Ngram, mixPick);
			posVec2.add(vec);
		}
		for (String neg : negTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg,
					Ngram, mixPick);
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
