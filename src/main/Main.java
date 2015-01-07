package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import UI.Dashboard;
import model.Classifier;
import model.DataSieve;
import model.DecisionStump;
import model.LanguageModel;
import model.ModelUtilities;
import model.PassiveAggressive;
import model.WinnowMachine;
import model.nGram;

//test
public class Main {
	public static LanguageModel LMmachine;
	public static WinnowMachine MLmachine;
	public static PassiveAggressive PAmachine, meetingMachine;
	public static DecisionStump lv1DecisionTree;
	public static Loader loader;
	public static ArrayList<nGram> posPick, negPick, mixPick;
	public static TreeSet<nGram> posPickSet, negPickSet, mixPickSet;
	public static TreeMap<nGram, Integer> mixPickPosMap;
	public static String outputPath;
	public static ArrayList<TreeMap<Integer, Double>> posTrainVec,
			posTrainOccVec;
	public static ArrayList<TreeMap<Integer, Double>> negTrainVec,
			negTrainOccVec;
	public static ArrayList<TreeMap<nGram, Integer>> posTrainOccGramVec,
			negTrainOccGramVec;
	public static ArrayList<TreeMap<Integer, Double>> posTestVec;
	public static ArrayList<TreeMap<Integer, Double>> negTestVec;
	public static String[] posTest, posTrain, negTest, negTrain, unknownTest;

	public static void main(String[] args) {
		// Dashboard demo = new Dashboard();
		work(3, 30000, "training_set");
	}

	public static void work(int Ngram, int topNgram, String trainingPath) {
		loader = new Loader(trainingPath);
		ShuffleChooser shuffleChooser = new ShuffleChooser(loader);
		ModelUtilities.addWordWeight(loader.wordWeight);
		outputPath = trainingPath + "/output";
		System.out.println("## Configuration ##\n");
		System.out.printf("* Ngram %d\n* topNgram %d\n", Ngram, topNgram);

		shuffleChooser.shuffle(1, 0);
		posTrain = shuffleChooser.posTrain
				.toArray(new String[shuffleChooser.posTrain.size()]);
		negTrain = shuffleChooser.negTrain
				.toArray(new String[shuffleChooser.negTrain.size()]);

		experiment(Ngram, topNgram);

		posTest = loader.testPos.toArray(new String[loader.testPos.size()]);
		negTest = loader.testNeg.toArray(new String[loader.testNeg.size()]);
		unknownTest = loader.testUnknown.toArray(new String[loader.testUnknown
				.size()]);
		processUserInput(Ngram, topNgram, posTest, negTest, unknownTest);
	}

	public static void processUserInput(int Ngram, int topNgram,
			String[] posTest, String[] negTest, String[] unkTest) {
		topNgram = mixPick.size();
		ArrayList<TreeMap<Integer, Double>> posVec2 = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec2 = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> posOccVec2 = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negOccVec2 = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<nGram, Integer>> posOccGramVec2 = new ArrayList<TreeMap<nGram, Integer>>();
		ArrayList<TreeMap<nGram, Integer>> negOccGramVec2 = new ArrayList<TreeMap<nGram, Integer>>();

		preprocessInput(Ngram, posTest, negTest, posVec2, negVec2, posOccVec2,
				negOccVec2, posOccGramVec2, negOccGramVec2);

		System.out.println("\n# User Require #\n");
		OutputClassifier.testLMClassifier(mixPick, LMmachine, posOccGramVec2,
				negOccGramVec2);
		OutputClassifier.testClassifier("Winnow", MLmachine, posVec2, negVec2);
		OutputClassifier.testClassifier("Passive-Aggressive", PAmachine,
				posVec2, negVec2);
		OutputClassifier.testSimpleDecision(lv1DecisionTree, posVec2, negVec2,
				posOccVec2, negOccVec2);
		OutputClassifier
				.testMeetingInterview(lv1DecisionTree, meetingMachine,
						LMmachine, MLmachine, PAmachine, Ngram, topNgram,
						posVec2, negVec2, posOccVec2, negOccVec2,
						posOccGramVec2, negOccGramVec2);
	}

	public static void experiment(int Ngram, int topNgram) {
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

		posPick = posSieve.getBestNgram(topNgram / 2);
		negPick = negSieve.getBestNgram(topNgram / 2);
		posPickSet = new TreeSet<nGram>();
		negPickSet = new TreeSet<nGram>();
		mixPick = ModelUtilities.mergePick(posPick, negPick, topNgram,
				posPickSet, negPickSet);

		for (int i = 0; i < 1000 && i < mixPick.size(); i++) {
			nGram e = mixPick.get(i);
			System.out.printf("Score %f (", e.score);
			for (int j = 0; j < e.iWord.length; j++) {
				System.out
						.printf("%s ", ModelUtilities.getWordName(e.iWord[j]));
			}
			System.out.println(")");
		}
		// System.exit(0);
		mixPickSet = new TreeSet<nGram>();
		mixPickPosMap = new TreeMap<nGram, Integer>();
		for (int i = 0; i < mixPick.size(); i++) {
			mixPickPosMap.put(mixPick.get(i), i);
			mixPickSet.add(mixPick.get(i));
		}
		topNgram = mixPick.size();

		posTrainVec = new ArrayList<TreeMap<Integer, Double>>();
		negTrainVec = new ArrayList<TreeMap<Integer, Double>>();
		posTrainOccVec = new ArrayList<TreeMap<Integer, Double>>();
		negTrainOccVec = new ArrayList<TreeMap<Integer, Double>>();
		posTrainOccGramVec = new ArrayList<TreeMap<nGram, Integer>>();
		negTrainOccGramVec = new ArrayList<TreeMap<nGram, Integer>>();

		preprocessInput(Ngram, posTrain, negTrain, posTrainVec, negTrainVec,
				posTrainOccVec, negTrainOccVec, posTrainOccGramVec,
				negTrainOccGramVec);

		lv1DecisionTree = createSimpleDecision();
		LMmachine = createLanguageModel(Ngram, topNgram, posTrainOccGramVec,
				negTrainOccGramVec);
		MLmachine = createWinnow(topNgram);
		PAmachine = createPassiveAggressive(topNgram);
		meetingMachine = createMeeting(LMmachine, MLmachine, PAmachine,
				lv1DecisionTree, Ngram, topNgram, mixPick, posTrain, negTrain);
	}

	public static void preprocessInput(int Ngram, String[] posTrain,
			String[] negTrain, ArrayList<TreeMap<Integer, Double>> posTrainVec,
			ArrayList<TreeMap<Integer, Double>> negTrainVec,
			ArrayList<TreeMap<Integer, Double>> posTrainOccVec,
			ArrayList<TreeMap<Integer, Double>> negTrainOccVec,
			ArrayList<TreeMap<nGram, Integer>> posTrainOccGramVec,
			ArrayList<TreeMap<nGram, Integer>> negTrainOccGramVec) {
		for (String pos : posTrain) {
			posTrainVec.add(ModelUtilities.getCharacteristicWeightVector(pos,
					Ngram, mixPickPosMap));
			posTrainOccGramVec.add(ModelUtilities.getNgramOcc(pos, Ngram,
					mixPickPosMap));
		}
		for (String neg : negTrain) {
			negTrainVec.add(ModelUtilities.getCharacteristicWeightVector(neg,
					Ngram, mixPickPosMap));
			negTrainOccGramVec.add(ModelUtilities.getNgramOcc(neg, Ngram,
					mixPickPosMap));
		}
		for (int i = 0; i < posTrainVec.size(); i++) {
			TreeMap<Integer, Double> viewsVec = null, voteVec = new TreeMap<Integer, Double>();
			double[] voteArr = new double[3];
			viewsVec = posTrainVec.get(i);
			for (Entry<Integer, Double> e : viewsVec.entrySet()) {
				int x = e.getKey();
				if (posPickSet.contains(mixPick.get(x)))
					voteArr[0]++;
				if (negPickSet.contains(mixPick.get(x)))
					voteArr[1]++;
			}
			voteArr[2] = voteArr[0] + voteArr[1];
			for (int j = 0; j < 3; j++)
				voteVec.put(j, voteArr[j]);
			posTrainOccVec.add(voteVec);
		}

		for (int i = 0; i < negTrainVec.size(); i++) {
			TreeMap<Integer, Double> viewsVec = null, voteVec = new TreeMap<Integer, Double>();
			double[] voteArr = new double[3];
			viewsVec = negTrainVec.get(i);
			for (Entry<Integer, Double> e : viewsVec.entrySet()) {
				int x = e.getKey();
				if (posPickSet.contains(mixPick.get(x)))
					voteArr[0]++;
				if (negPickSet.contains(mixPick.get(x)))
					voteArr[1]++;
			}
			voteArr[2] = voteArr[0] + voteArr[1];
			for (int j = 0; j < 3; j++)
				voteVec.put(j, voteArr[j]);
			negTrainOccVec.add(voteVec);
		}
	}

	public static DecisionStump createSimpleDecision() {
		System.out.println("\n## Simple Decision ##\n");
		DecisionStump lv1DecisionTree = new DecisionStump(3);
		ArrayList<TreeMap<Integer, Double>> votePosVec = posTrainOccVec;
		ArrayList<TreeMap<Integer, Double>> voteNegVec = negTrainOccVec;

		int ITLIMIT = 80;
		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf(">");
			int posIdx = 0, negIdx = 0;
			while (posIdx < votePosVec.size() || negIdx < voteNegVec.size()) {
				if (Math.random() < 0.5 && posIdx < votePosVec.size()) {
					lv1DecisionTree.add(votePosVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < voteNegVec.size()) {
					lv1DecisionTree.add(voteNegVec.get(negIdx), -1);
					negIdx++;
				}
			}
		}
		System.out.printf("|\n\n");
		int posIdx = 0, negIdx = 0;
		while (posIdx < votePosVec.size() || negIdx < voteNegVec.size()) {
			if (Math.random() < 0.5 && posIdx < votePosVec.size()) {
				lv1DecisionTree.selfTraining(votePosVec.get(posIdx), 1);
				posIdx++;
			} else if (negIdx < voteNegVec.size()) {
				lv1DecisionTree.selfTraining(voteNegVec.get(negIdx), -1);
				negIdx++;
			}
		}
		return lv1DecisionTree;
	}

	public static PassiveAggressive createMeeting(LanguageModel LMmachine,
			WinnowMachine MLmachine, PassiveAggressive PAmachine,
			DecisionStump lv1DecisionTree, int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posTrain, String[] negTrain) {
		System.out.println("\n## Adaboost ##\n");
		System.out.printf("* Prepare Meeting Machine ...\n\n");
		PassiveAggressive meetingMachine = new PassiveAggressive(8);
		ArrayList<TreeMap<Integer, Double>> votePosVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> voteNegVec = new ArrayList<TreeMap<Integer, Double>>();

		boolean[] predict = new boolean[4];
		double[] predictWeight = new double[4];

		for (int i = 0; i < posTrainVec.size(); i++) {
			String views = null;
			TreeMap<Integer, Double> viewsVec = null, voteVec = new TreeMap<Integer, Double>();
			double[] voteArr = new double[8];
			views = posTrain[i];
			viewsVec = posTrainVec.get(i);

			predict[0] = LMmachine.classify(posTrainOccGramVec.get(i));
			predict[1] = MLmachine.classify(viewsVec);
			predict[2] = PAmachine.classify(viewsVec);
			predict[3] = lv1DecisionTree.classify(posTrainOccVec.get(i));

			// predictWeight[0] = LMmachine.strongClassify(views);
			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);
			predictWeight[3] = lv1DecisionTree.strongClassify(posTrainOccVec
					.get(i));
			predictWeight[0] = 1;
			predictWeight[3] = 1;

			// voteArr[0] = LMmachine.strongClassify(views, "pos");
			// voteArr[4] = -LMmachine.strongClassify(views, "neg");
			for (int j = 0; j < 4; j++) {
				if (predict[j])
					voteArr[j] = predictWeight[j];
				else
					voteArr[4 + j] = -predictWeight[j];
			}
			for (int j = 0; j < 8; j++)
				voteVec.put(j, voteArr[j]);
			votePosVec.add(voteVec);
		}

		for (int i = 0; i < negTrainVec.size(); i++) {
			String views = null;
			TreeMap<Integer, Double> viewsVec = null, voteVec = new TreeMap<Integer, Double>();
			double[] voteArr = new double[8];
			views = negTrain[i];
			viewsVec = negTrainVec.get(i);

			predict[0] = LMmachine.classify(negTrainOccGramVec.get(i));
			predict[1] = MLmachine.classify(viewsVec);
			predict[2] = PAmachine.classify(viewsVec);
			predict[3] = lv1DecisionTree.classify(negTrainOccVec.get(i));

			predictWeight[0] = LMmachine.strongClassify(negTrainOccGramVec
					.get(i));
			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);
			predictWeight[3] = lv1DecisionTree.strongClassify(negTrainOccVec
					.get(i));
			predictWeight[0] = 1;
			predictWeight[3] = 1;

			// voteArr[0] = LMmachine.strongClassify(views, "pos");
			// voteArr[4] = -LMmachine.strongClassify(views, "neg");
			for (int j = 0; j < 4; j++) {
				if (predict[j])
					voteArr[j] = predictWeight[j];
				else
					voteArr[4 + j] = -predictWeight[j];
			}
			for (int j = 0; j < 8; j++)
				voteVec.put(j, voteArr[j]);
			voteNegVec.add(voteVec);
		}

		int ITLIMIT = 80;
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

	public static LanguageModel createLanguageModel(int Ngram, int topNgram,
			ArrayList<TreeMap<nGram, Integer>> posTrainOccGramVec,
			ArrayList<TreeMap<nGram, Integer>> negTrainOccGramVec) {
		System.out.println("\n## Language Model ##\n");
		System.out.printf("* Language Model prepare ...\n");
		LanguageModel LMmachine = new LanguageModel(Ngram);
		for (TreeMap<nGram, Integer> pos : posTrainOccGramVec)
			LMmachine.add(pos, "pos");
		for (TreeMap<nGram, Integer> neg : negTrainOccGramVec)
			LMmachine.add(neg, "neg");

		System.out.printf("* Language Model self-testing ...\n");

		for (TreeMap<nGram, Integer> pos : posTrainOccGramVec)
			LMmachine.selfTraining(pos, "pos");
		for (TreeMap<nGram, Integer> neg : negTrainOccGramVec)
			LMmachine.selfTraining(neg, "neg");

		return LMmachine;
	}

	public static WinnowMachine createWinnow(int topNgram) {
		System.out.println("\n## Winnow Algorithm ##\n");
		System.out.printf("* Winnow algorithm prepare ...\n");
		WinnowMachine MLmachine = new WinnowMachine(topNgram);
		int ITLIMIT = 50;

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf(">");
			int posIdx = 0, negIdx = 0;
			while (posIdx < posTrainVec.size() || negIdx < negTrainVec.size()) {
				if (Math.random() < 0.5 && posIdx < posTrainVec.size()) {
					MLmachine.add(posTrainVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < negTrainVec.size()) {
					MLmachine.add(negTrainVec.get(negIdx), 0);
					negIdx++;
				}
			}
		}
		System.out.printf("|\n\n");

		System.out.printf("* Winnow algorithm self-testing ...\n");
		int posIdx = 0, negIdx = 0;
		while (posIdx < posTrainVec.size() || negIdx < negTrainVec.size()) {
			if (Math.random() < 0.5 && posIdx < posTrainVec.size()) {
				MLmachine.selfTraining(posTrainVec.get(posIdx), 1);
				posIdx++;
			} else if (negIdx < negTrainVec.size()) {
				MLmachine.selfTraining(negTrainVec.get(negIdx), 0);
				negIdx++;
			}
		}
		// System.out.printf("%f %f\n", MLmachine.selfMin, MLmachine.selfMax);

		return MLmachine;
	}

	public static PassiveAggressive createPassiveAggressive(int topNgram) {
		System.out.println("## Passive-Aggressive Algorithm ##\n");
		System.out
				.printf("* Passive-Aggressive algorithm top-%d prepare ...\n",
						topNgram);
		PassiveAggressive PAmachine = new PassiveAggressive(topNgram);
		int ITLIMIT = 50;

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			System.out.printf(">");
			int posIdx = 0, negIdx = 0;
			while (posIdx < posTrainVec.size() || negIdx < negTrainVec.size()) {
				if (Math.random() < 0.5 && posIdx < posTrainVec.size()) {
					PAmachine.add(posTrainVec.get(posIdx), 1);
					posIdx++;
				} else if (negIdx < negTrainVec.size()) {
					PAmachine.add(negTrainVec.get(negIdx), -1);
					negIdx++;
				}
			}
		}
		System.out.printf("|\n\n");
		System.out.printf("* Passive-Aggressive algorithm self-testing ...\n");
		int posIdx = 0, negIdx = 0;
		while (posIdx < posTrainVec.size() || negIdx < negTrainVec.size()) {
			if (Math.random() < 0.5 && posIdx < posTrainVec.size()) {
				PAmachine.selfTraining(posTrainVec.get(posIdx), 1);
				posIdx++;
			} else if (negIdx < negTrainVec.size()) {
				PAmachine.selfTraining(negTrainVec.get(negIdx), -1);
				negIdx++;
			}
		}
		// System.out.printf("%f %f\n", PAmachine.selfMin, PAmachine.selfMax);

		return PAmachine;
	}
}
