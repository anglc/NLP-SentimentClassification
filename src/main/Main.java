package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Arrays;
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
	public static WinnowMachine MLmachine[];
	public static PassiveAggressive PAmachine[], meetingMachine;
	public static DecisionStump lv1DecisionTree;
	public static Loader loader;
	public static ArrayList<nGram> posPick, negPick, mixPick;
	public static TreeSet<nGram> posPickSet, negPickSet, mixPickSet;
	public static TreeMap<nGram, Integer> mixPickPosMap;
	public static String outputPath;
	public static ArrayList<Article> posTrainArticles, negTrainArticles;
	public static ArrayList<TreeMap<Integer, Double>> posTestVec;
	public static ArrayList<TreeMap<Integer, Double>> negTestVec;
	public static String[] posTest, posTrain, negTest, negTrain;
	public final static int ITLIMIT = 32;

	public static void main(String[] args) {
		// Dashboard demo = new Dashboard();
		work(3, 40000, "training_set");
	}

	public static void work(int Ngram, int topNgram, String trainingPath) {
		loader = new Loader(trainingPath);
		ShuffleChooser shuffleChooser = new ShuffleChooser(loader);
		outputPath = trainingPath + "/output";
		System.out.println("## Configuration ##\n");
		System.out.printf("* Ngram %d\n* topNgram %d\n", Ngram, topNgram);

		shuffleChooser.shuffle(1, 0);
		posTrain = shuffleChooser.posTrain
				.toArray(new String[shuffleChooser.posTrain.size()]);
		negTrain = shuffleChooser.negTrain
				.toArray(new String[shuffleChooser.negTrain.size()]);

		posTrainArticles = new ArrayList<Article>();
		negTrainArticles = new ArrayList<Article>();

		for (int i = 0; i < posTrain.length; i++)
			posTrainArticles.add(new Article(posTrain[i], 1));
		for (int i = 0; i < negTrain.length; i++)
			negTrainArticles.add(new Article(negTrain[i], -1));

		experiment(Ngram, topNgram);

		posTest = loader.testPos.toArray(new String[loader.testPos.size()]);
		negTest = loader.testNeg.toArray(new String[loader.testNeg.size()]);

		processUserInput(Ngram, topNgram, posTest, negTest);
	}

	public static void processUserInput(int Ngram, int topNgram,
			String[] posTest, String[] negTest) {
		topNgram = mixPick.size();
		ArrayList<Article> posTrainArticles2, negTrainArticles2;
		posTrainArticles2 = new ArrayList<Article>();
		negTrainArticles2 = new ArrayList<Article>();
		for (int i = 0; i < posTest.length; i++)
			posTrainArticles2.add(new Article(posTest[i], 1));
		for (int i = 0; i < negTest.length; i++)
			negTrainArticles2.add(new Article(negTest[i], -1));

		preprocessInput(Ngram, posTrainArticles2, negTrainArticles2);

		ArrayList<TreeMap<Integer, Double>> posVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec = new ArrayList<TreeMap<Integer, Double>>();
		for (Article pos : posTrainArticles2)
			posVec.add(pos.vec);
		for (Article neg : negTrainArticles2)
			negVec.add(neg.vec);
		System.out.println("\n# User Require #\n");
		OutputClassifier.testLMClassifier(mixPick, LMmachine,
				posTrainArticles2, negTrainArticles2);
		OutputClassifier.testClassifier("Winnow", MLmachine[0], posVec, negVec);
		OutputClassifier.testClassifier("Passive-Aggressive", PAmachine[0],
				posVec, negVec);
		OutputClassifier.testSimpleDecision(lv1DecisionTree, posTrainArticles2,
				negTrainArticles2);
		OutputClassifier.testMeetingInterview(lv1DecisionTree, meetingMachine,
				LMmachine, MLmachine, PAmachine, Ngram, topNgram,
				posTrainArticles2, negTrainArticles2);
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

		PickTool pickTool = new PickTool(posPick, negPick);

		posPickSet = new TreeSet<nGram>();
		negPickSet = new TreeSet<nGram>();
		mixPick = pickTool.getMixPick(topNgram, posPickSet, negPickSet);
		mixPickSet = new TreeSet<nGram>();
		mixPickPosMap = new TreeMap<nGram, Integer>();
		for (int i = 0; i < mixPick.size(); i++) {
			mixPickPosMap.put(mixPick.get(i), i);
			mixPickSet.add(mixPick.get(i));
		}
		topNgram = mixPick.size();
		System.out.printf("* actual #top-Ngrams %d\n", topNgram);
		preprocessInput(Ngram, posTrainArticles, negTrainArticles);

		MLmachine = new WinnowMachine[3];
		PAmachine = new PassiveAggressive[3];

		System.out.println("\n## Simple Decision ##\n");
		lv1DecisionTree = createSimpleDecision(posTrainArticles,
				negTrainArticles);
		System.out.println("\n## Language Model ##\n");
		LMmachine = createLanguageModel(Ngram, topNgram, posTrainArticles,
				negTrainArticles);
		System.out.println("\n## Winnow Algorithm ##\n");
		for (int i = 0; i < MLmachine.length; i++)
			MLmachine[i] = createWinnow(topNgram, posTrainArticles,
					negTrainArticles);
		System.out.println("## Passive-Aggressive Algorithm ##\n");
		for (int i = 0; i < PAmachine.length; i++)
			PAmachine[i] = createPassiveAggressive(topNgram, posTrainArticles,
					negTrainArticles);
		System.out.println("\n## Adaboost ##\n");
		meetingMachine = createMeeting(LMmachine, MLmachine, PAmachine,
				lv1DecisionTree, Ngram, topNgram, mixPick, posTrainArticles,
				negTrainArticles);

	}

	public static void preprocessInput(int Ngram,
			ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles) {
		int n = posTrainArticles.size();
		int m = negTrainArticles.size();
		System.out
				.printf("\npreprocess %d pos, %d neg-items to vector\n", n, m);
		for (int i = 0; i < n; i++)
			posTrainArticles.get(i).preprocess(Ngram, posPickSet, negPickSet,
					mixPick, mixPickPosMap);
		for (int i = 0; i < m; i++)
			negTrainArticles.get(i).preprocess(Ngram, posPickSet, negPickSet,
					mixPick, mixPickPosMap);
	}

	public static DecisionStump createSimpleDecision(
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		DecisionStump lv1DecisionTree = new DecisionStump(5);
		int n = posTrainArticles2.size();
		int m = negTrainArticles2.size();
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTrainArticles2);
		articles.addAll(negTrainArticles2);

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			if (it % (ITLIMIT / 10) == 0)
				System.out.printf(">");
			Collections.shuffle(articles);
			for (int i = 0; i < articles.size(); i++) {
				if (articles.get(i).polarity > 0) {
					lv1DecisionTree.add(articles.get(i).occVec, 1);
				} else {
					lv1DecisionTree.add(articles.get(i).occVec, -1);
				}
			}
		}
		System.out.printf("|\n\n");
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).polarity > 0) {
				lv1DecisionTree.selfTraining(articles.get(i).occVec, 1);
			} else {
				lv1DecisionTree.selfTraining(articles.get(i).occVec, -1);
			}
		}
		return lv1DecisionTree;
	}

	public static PassiveAggressive createMeeting(LanguageModel LMmachine,
			WinnowMachine[] MLmachine2, PassiveAggressive[] PAmachine2,
			DecisionStump lv1DecisionTree, int Ngram, int topNgram,
			ArrayList<nGram> mixPick, ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		int vectorSize = (2 + MLmachine2.length + PAmachine2.length) * 2;
		int base1 = 2, base2 = 2 + MLmachine2.length;
		PassiveAggressive meetingMachine = new PassiveAggressive(vectorSize);
		ArrayList<TreeMap<Integer, Double>> votePosVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> voteNegVec = new ArrayList<TreeMap<Integer, Double>>();
		int n = posTrainArticles2.size();
		int m = negTrainArticles2.size();
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTrainArticles2);
		articles.addAll(negTrainArticles2);

		for (Article article : articles) {
			double predictW;
			boolean predict;
			TreeMap<Integer, Double> voteVec = new TreeMap<Integer, Double>();

			predict = LMmachine.classify(article.occGramVec);
			predictW = 1;
			if (predict)
				voteVec.put(0 * 2, predictW);
			else
				voteVec.put(0 * 2 + 1, predictW);

			predict = lv1DecisionTree.classify(article.occVec);
			predictW = 1;
			if (predict)
				voteVec.put(1 * 2, predictW);
			else
				voteVec.put(1 * 2 + 1, predictW);
			
			for (int j = 0; j < MLmachine2.length; j++) {
				predict = MLmachine2[j].classify(article.vec);
				predictW = MLmachine2[j].strongClassify(article.vec);
				if (predict)
					voteVec.put((j + base1) * 2, predictW);
				else
					voteVec.put((j + base1) * 2 + 1, predictW);
			}
			
			for (int j = 0; j < PAmachine2.length; j++) {
				predict = PAmachine2[j].classify(article.vec);
				predictW = PAmachine2[j].strongClassify(article.vec);
				if (predict)
					voteVec.put((j + base2) * 2, predictW);
				else
					voteVec.put((j + base2) * 2 + 1, predictW);
			}

			if (article.polarity > 0)
				votePosVec.add(voteVec);
			else
				voteNegVec.add(voteVec);
		}

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT * 10; it++) {
			if (it % (ITLIMIT * 10 / 10) == 0)
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
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {

		LanguageModel LMmachine = new LanguageModel(Ngram);
		for (Article pos : posTrainArticles2)
			LMmachine.add(pos.occGramVec, "pos");
		for (Article neg : negTrainArticles2)
			LMmachine.add(neg.occGramVec, "neg");

		for (Article pos : posTrainArticles2)
			LMmachine.selfTraining(pos.occGramVec, "pos");
		for (Article neg : negTrainArticles2)
			LMmachine.selfTraining(neg.occGramVec, "neg");

		return LMmachine;
	}

	public static WinnowMachine createWinnow(int topNgram,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		int n = posTrainArticles2.size();
		int m = negTrainArticles2.size();
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTrainArticles2);
		articles.addAll(negTrainArticles2);
		WinnowMachine MLmachine = new WinnowMachine(topNgram);

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			if (it % (ITLIMIT / 10) == 0)
				System.out.printf(">");
			Collections.shuffle(articles);
			for (int i = 0; i < articles.size(); i++) {
				if (articles.get(i).polarity > 0) {
					MLmachine.add(articles.get(i).vec, 1);
				} else {
					MLmachine.add(articles.get(i).vec, 0);
				}
			}
		}
		System.out.printf("|\n\n");

		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).polarity > 0) {
				MLmachine.selfTraining(articles.get(i).vec, 1);
			} else {
				MLmachine.selfTraining(articles.get(i).vec, 0);
			}
		}

		return MLmachine;
	}

	public static PassiveAggressive createPassiveAggressive(int topNgram,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		int n = posTrainArticles2.size();
		int m = negTrainArticles2.size();
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTrainArticles2);
		articles.addAll(negTrainArticles2);

		PassiveAggressive PAmachine = new PassiveAggressive(topNgram);

		System.out.printf("\ncomplete |");
		for (int it = 0; it < ITLIMIT; it++) {
			if (it % (ITLIMIT / 10) == 0)
				System.out.printf(">");
			Collections.shuffle(articles);
			for (int i = 0; i < articles.size(); i++) {
				if (articles.get(i).polarity > 0) {
					PAmachine.add(articles.get(i).vec, 1);
				} else {
					PAmachine.add(articles.get(i).vec, -1);
				}
			}
		}
		System.out.printf("|\n\n");

		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).polarity > 0) {
				PAmachine.selfTraining(articles.get(i).vec, 1);
			} else {
				PAmachine.selfTraining(articles.get(i).vec, -1);
			}
		}

		return PAmachine;
	}
}
