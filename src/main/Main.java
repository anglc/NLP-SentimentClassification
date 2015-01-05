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
//test
public class Main {

	public static LanguageModel LMmachine;
	public static WinnowMachine MLmachine;
	public static PassiveAggressive PAmachine, meetingMachine;
	public static Loader loader;
	public static ArrayList<nGram> posPick, negPick, mixPick;
	public static String outputPath;

	public static void main(String[] args) {
		// Dashboard demo = new Dashboard();
		work(4, 40000, "training_set", 1, 0);
	}

	public static void work(int Ngram, int topNgram, String trainingPath,
			int trainingRatio, int testingRatio) {
		loader = new Loader(trainingPath);
		ShuffleChooser shuffleChooser = new ShuffleChooser(loader);
		ModelUtilities.addWordWeight(loader.wordWeight);
		String[] posTest, posTrain, negTest, negTrain, unknownTest;
		outputPath = trainingPath + "/output";
		System.out.println("## Configuration ##\n");
		System.out.printf("* Ngram %d\n* topNgram %d\n", Ngram, topNgram);

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

		posTest = loader.testPos.toArray(new String[loader.testPos.size()]);
		negTest = loader.testNeg.toArray(new String[loader.testNeg.size()]);
		unknownTest = loader.testUnknown.toArray(new String[loader.testUnknown
				.size()]);
		processUserInput(Ngram, topNgram, posTest, negTest, unknownTest);
	}

	public static void processUserInput(int Ngram, int topNgram,
			String[] posTest, String[] negTest, String[] unkTest) {
		topNgram = mixPick.size();
		ArrayList<double[]> posVec2 = new ArrayList<double[]>();
		ArrayList<double[]> negVec2 = new ArrayList<double[]>();
		ArrayList<double[]> unkVec2 = new ArrayList<double[]>();

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
		for (String unk : unkTest) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(unk,
					Ngram, mixPick);
			unkVec2.add(vec);
		}
		System.out.println("\n# User Require #\n");
		OutputClassifier.testLMClassifier(mixPick, LMmachine, posTest, negTest,
				unkTest);
		OutputClassifier.testClassifier("Winnow", MLmachine, posVec2, negVec2,
				unkVec2);
		OutputClassifier.testClassifier("Passive-Aggressive", PAmachine,
				posVec2, negVec2, unkVec2);
		OutputClassifier.testMeetingInterview(meetingMachine, LMmachine,
				MLmachine, PAmachine, Ngram, topNgram, mixPick, posTest,
				negTest, unkTest, posVec2, negVec2, unkVec2);
	}

	public static void experiment(int Ngram, int topNgram, String[] posTrain,
			String[] negTrain, String[] posTest, String[] negTest) {
		System.out.println("\n## Work ##\n");
		System.out.printf("* positive sieve %d-grams building ...\n", Ngram);
		DataSieve posSieve = new DataSieve(Ngram, posTrain, negTrain);

		// posSieve.printBestNgram(20);
		// System.exit(0);

		System.out.printf("* negative sieve %d-grams building ...\n\n", Ngram);
		DataSieve negSieve = new DataSieve(Ngram, negTrain, posTrain);

		// negSieve.printBestNgram(20);
		// System.exit(0);

		System.out.printf("* positive #ngram %d\n", posSieve.ngramCount);
		System.out.printf("* negative #ngram %d\n", negSieve.ngramCount);

		posPick = posSieve.getBestNgram(topNgram);
		negPick = negSieve.getBestNgram(topNgram);
		mixPick = ModelUtilities.mergePick(posPick, negPick, topNgram);

		topNgram = mixPick.size();

		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		ArrayList<double[]> posVec2 = new ArrayList<double[]>();
		ArrayList<double[]> negVec2 = new ArrayList<double[]>();
		for (String pos : posTrain) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(pos,
					Ngram, mixPick);
			posVec.add(vec);
		}
		for (String neg : negTrain) {
			double[] vec = ModelUtilities.getCharacteristicWeightVector(neg,
					Ngram, mixPick);
			negVec.add(vec);
		}
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

		LMmachine = testLanguageModel(Ngram, topNgram, mixPick, posTrain,
				negTrain, posTest, negTest);
		MLmachine = testWinnow(topNgram, posVec, negVec, posVec2, negVec2);
		PAmachine = testPassiveAggressive(topNgram, posVec, negVec, posVec2,
				negVec2);
		meetingMachine = prepareMeeting(LMmachine, MLmachine, PAmachine, Ngram,
				topNgram, mixPick, posTrain, negTrain, posVec, negVec);
		testMeetingInterview(meetingMachine, LMmachine, MLmachine, PAmachine,
				Ngram, topNgram, mixPick, posTest, negTest, posVec2, negVec2);
	}

	public static PassiveAggressive prepareMeeting(LanguageModel LMmachine,
			WinnowMachine MLmachine, PassiveAggressive PAmachine, int Ngram,
			int topNgram, ArrayList<nGram> mixPick, String[] posTrain,
			String[] negTrain, ArrayList<double[]> posVec,
			ArrayList<double[]> negVec) {
		System.out.println("\n## Adaboost ##\n");
		System.out.printf("* Prepare Meeting Machine ...\n\n");
		PassiveAggressive meetingMachine = new PassiveAggressive(6);
		ArrayList<double[]> votePosVec = new ArrayList<double[]>();
		ArrayList<double[]> voteNegVec = new ArrayList<double[]>();

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

			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);

			voteArr[0] = LMmachine.strongClassify(views, "pos", mixPick);
			voteArr[0 + 3] = -LMmachine.strongClassify(views, "neg", mixPick);
			for (int j = 1; j < 3; j++) {
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

			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);

			voteArr[0] = LMmachine.strongClassify(views, "pos", mixPick);
			voteArr[0 + 3] = -LMmachine.strongClassify(views, "neg", mixPick);
			for (int j = 1; j < 3; j++) {
				if (predict[j])
					voteArr[j] = predictWeight[j];
				else
					voteArr[j + 3] = -predictWeight[j];
			}
			voteNegVec.add(voteArr);
		}

		int ITLIMIT = 40;
		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf(">");
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
		System.out.printf("|\n\n");

		return meetingMachine;
	}

	public static void testMeetingInterview(PassiveAggressive meetingMachine,
			LanguageModel LMmachine, WinnowMachine MLmachine,
			PassiveAggressive PAmachine, int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posTest, String[] negTest,
			ArrayList<double[]> posVec2, ArrayList<double[]> negVec2) {
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();

		System.out.printf("* Meeting prepare ...\n");
		System.out.printf("* Meeting top-%d testing ...\n\n", topNgram);

		int posIdx = 0, negIdx = 0;
		boolean[] predict = new boolean[3];
		double[] predictWeight = new double[3];

		while (posIdx < posTest.length || negIdx < negTest.length) {
			boolean correct = false;
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
			if (correct)
				posVec.add(voteArr);
			else
				negVec.add(voteArr);
		}
		testClassifier("Adaboost", meetingMachine, posVec, negVec);
		return;
	}

	public static LanguageModel testLanguageModel(int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posViews, String[] negViews,
			String[] posTest, String[] negTest) {
		System.out.println("\n## Language Model ##\n");
		System.out.printf("* Language Model prepare ...\n");
		LanguageModel LMmachine = new LanguageModel(Ngram);
		for (String pos : posViews)
			LMmachine.add(pos, "pos", mixPick);
		for (String neg : negViews)
			LMmachine.add(neg, "neg", mixPick);

		System.out.printf("* Language Model self-testing ...\n");

		for (String pos : posViews)
			LMmachine.selfTraining(pos, "pos", mixPick);
		for (String neg : negViews)
			LMmachine.selfTraining(neg, "neg", mixPick);

		System.out.printf("* Language Model testing ...\n\n");
		testLMClassifier(LMmachine, posTest, negTest);
		return LMmachine;
	}

	public static WinnowMachine testWinnow(int topNgram,
			ArrayList<double[]> posVec, ArrayList<double[]> negVec,
			ArrayList<double[]> posVec2, ArrayList<double[]> negVec2) {
		System.out.println("\n## Winnow Algorithm ##\n");
		System.out.printf("* Winnow algorithm prepare ...\n");
		WinnowMachine MLmachine = new WinnowMachine(topNgram);
		int ITLIMIT = 40;

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf(">");
			int posIdx = 0, negIdx = 0;
			while (posIdx < posVec.size() || negIdx < negVec.size()) {
				if (Math.random() < 0.5 && posIdx < posVec.size()) {
					MLmachine.add(posVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < negVec.size()) {
					MLmachine.add(negVec.get(negIdx), 0);
					negIdx++;
				}
			}
		}
		System.out.printf("|\n\n");

		System.out.printf("* Winnow algorithm self-testing ...\n");
		int posIdx = 0, negIdx = 0;
		while (posIdx < posVec.size() || negIdx < negVec.size()) {
			if (Math.random() < 0.5 && posIdx < posVec.size()) {
				MLmachine.selfTraining(posVec.get(posIdx), 1);
				posIdx++;
			} else if (negIdx < negVec.size()) {
				MLmachine.selfTraining(negVec.get(negIdx), 0);
				negIdx++;
			}
		}
		// System.out.printf("%f %f\n", MLmachine.selfMin, MLmachine.selfMax);

		System.out.printf("* Winnow algorithm testing ...\n\n");
		testClassifier("Winnow", MLmachine, posVec2, negVec2);
		return MLmachine;
	}

	public static PassiveAggressive testPassiveAggressive(int topNgram,
			ArrayList<double[]> posVec, ArrayList<double[]> negVec,
			ArrayList<double[]> posVec2, ArrayList<double[]> negVec2) {
		System.out.println("## Passive-Aggressive Algorithm ##\n");
		System.out
				.printf("* Passive-Aggressive algorithm top-%d prepare ...\n",
						topNgram);
		PassiveAggressive PAmachine = new PassiveAggressive(topNgram);
		int ITLIMIT = 40;

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf(">");
			int posIdx = 0, negIdx = 0;
			while (posIdx < posVec.size() || negIdx < negVec.size()) {
				if (Math.random() < 0.5 && posIdx < posVec.size()) {
					PAmachine.add(posVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < negVec.size()) {
					PAmachine.add(negVec.get(negIdx), -1);
					negIdx++;
				}
			}
		}
		System.out.printf("|\n\n");
		System.out.printf("* Passive-Aggressive algorithm self-testing ...\n");

		int posIdx = 0, negIdx = 0;
		while (posIdx < posVec.size() || negIdx < negVec.size()) {
			if (Math.random() < 0.5 && posIdx < posVec.size()) {
				PAmachine.selfTraining(posVec.get(posIdx), 1);
				posIdx++;
			} else if (negIdx < negVec.size()) {
				PAmachine.selfTraining(negVec.get(negIdx), -1);
				negIdx++;
			}
		}
		// System.out.printf("%f %f\n", PAmachine.selfMin, PAmachine.selfMax);

		System.out.printf("* Passive-Aggressive algorithm testing ...\n\n");

		testClassifier("Passive-Aggressive", PAmachine, posVec2, negVec2);
		return PAmachine;
	}

	public static void testClassifier(String algName, Classifier classifier,
			ArrayList<double[]> posVec, ArrayList<double[]> negVec) {
		if (posVec.size() == 0 || negVec.size() == 0)
			return;
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

	public static void testLMClassifier(LanguageModel LMmachine,
			String[] posTest, String[] negTest) {
		if (posTest.length == 0 || negTest.length == 0)
			return;
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
	}
}
