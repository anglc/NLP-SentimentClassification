package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
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
	public static WinnowMachine MLmachines[];
	public static PassiveAggressive PAmachines[], meetingMachine;
	public static DecisionStump lv1DecisionTree;
	public static Loader loader;
	public static String outputPath;
	public static String[] posTest, posTrain, negTest, negTrain;
	public final static int ITLIMIT = 32;
	public static int stdout_status = 1;

	public static void main(String[] args) {
		// Dashboard demo = new Dashboard();
		work(3, 40000, "training_set");
	}

	public static void stdout(String s, int priority) {
		if (stdout_status == 1 && priority > 0)
			System.out.print(s);
	}

	public static void work(int Ngram, int topNgram, String trainingPath) {
		loader = new Loader(trainingPath);
		ShuffleChooser shuffleChooser = new ShuffleChooser(loader);
		outputPath = trainingPath + "/output";
		System.out.println("## Configuration ##\n");
		System.out.printf("* Ngram %d\n* topNgram %d\n", Ngram, topNgram);

		TreeMap<nGram, Double> ngramScoreMap = new TreeMap<nGram, Double>();
		TreeSet<nGram> posPickSet = null, negPickSet = null, mixPickSet = null;
		TreeMap<nGram, Integer> mixPickPosMap = null;
		ArrayList<nGram> mixPick = null;
		for (int cross = 0; cross < 5; cross++) { // 2-fold cross-validation
			shuffleChooser.shuffle(1, 1);
			posTrain = shuffleChooser.posTrain
					.toArray(new String[shuffleChooser.posTrain.size()]);
			negTrain = shuffleChooser.negTrain
					.toArray(new String[shuffleChooser.negTrain.size()]);

			ArrayList<Article> posTrainArticles, negTrainArticles;
			posTrainArticles = new ArrayList<Article>();
			negTrainArticles = new ArrayList<Article>();

			for (int i = 0; i < posTrain.length; i++)
				posTrainArticles.add(new Article(posTrain[i], 1));
			for (int i = 0; i < negTrain.length; i++)
				negTrainArticles.add(new Article(negTrain[i], -1));

			posPickSet = new TreeSet<nGram>();
			negPickSet = new TreeSet<nGram>();
			mixPickSet = new TreeSet<nGram>();
			mixPickPosMap = new TreeMap<nGram, Integer>();

			stdout(String.format("\n# Cross-validation Work %d #\n\n", cross),
					1);

			mixPick = experiment(Ngram, topNgram, posTrainArticles,
					negTrainArticles, posPickSet, negPickSet, mixPickSet,
					mixPickPosMap, null);
			posTest = shuffleChooser.posTest
					.toArray(new String[shuffleChooser.posTest.size()]);
			negTest = shuffleChooser.negTest
					.toArray(new String[shuffleChooser.negTest.size()]);

			MeasureInfo performance;
			performance = processTestInput(Ngram, topNgram, posTest, negTest,
					mixPick, posPickSet, negPickSet, mixPickSet, mixPickPosMap);

			System.out.printf("performance %f\n\n", performance.P * 100);

			for (int i = 0; i < mixPick.size(); i++) {
				nGram e = mixPick.get(i);
				double score = performance.P, w = 1;
				// for (int j = 0; j < PAmachine.length; j++)
				// w += Math.abs(PAmachine[j].w[i]);
				score *= w;
				if (ngramScoreMap.containsKey(e))
					score += ngramScoreMap.get(e);
				ngramScoreMap.put(e, score);
			}
		}

		afterTraining(Ngram, topNgram, loader, shuffleChooser, ngramScoreMap);
	}

	public static void afterTraining(int Ngram, int topNgram, Loader loader,
			ShuffleChooser shuffleChooser, TreeMap<nGram, Double> ngramScoreMap) {
		ArrayList<Pair<nGram, Double>> ngramScoreArr = new ArrayList<Pair<nGram, Double>>();
		TreeSet<nGram> retainNgram = new TreeSet<nGram>();
		int retainNgramSize = ngramScoreMap.size() / 5;

		for (Map.Entry<nGram, Double> e : ngramScoreMap.entrySet()) {
			Pair<nGram, Double> p;
			p = new Pair<nGram, Double>(e.getKey(), e.getValue());
			ngramScoreArr.add(p);
		}
		Collections.sort(ngramScoreArr, new Comparator<Pair<nGram, Double>>() {
			@Override
			public int compare(Pair<nGram, Double> a, Pair<nGram, Double> b) {
				int c = Double.compare(a.getSecond(), b.getSecond());
				return -c;
			}
		});
		for (int i = 0; i < retainNgramSize && i < ngramScoreArr.size(); i++)
			retainNgram.add(ngramScoreArr.get(i).getFirst());

		// for (int i = 0; i < 100 && i < ngramScoreArr.size(); i++) {
		// nGram e = ngramScoreArr.get(i).getFirst();
		// System.out.printf("Score ( ");
		// for (int j = 0; j < e.iWord.length; j++) {
		// System.out
		// .printf("%s ", ModelUtilities.getWordName(e.iWord[j]));
		// }
		// System.out.println(")");
		// }

		TreeSet<nGram> posPickSet = null, negPickSet = null, mixPickSet = null;
		TreeMap<nGram, Integer> mixPickPosMap = null;
		ArrayList<nGram> mixPick = null;
		shuffleChooser.shuffle(7, 3);
		posTrain = shuffleChooser.posTrain
				.toArray(new String[shuffleChooser.posTrain.size()]);
		negTrain = shuffleChooser.negTrain
				.toArray(new String[shuffleChooser.negTrain.size()]);

		posPickSet = new TreeSet<nGram>();
		negPickSet = new TreeSet<nGram>();
		mixPickSet = new TreeSet<nGram>();
		mixPickPosMap = new TreeMap<nGram, Integer>();

		stdout(String.format("\n# Final Work #\n\n"), 1);

		ArrayList<Article> posTrainArticles, negTrainArticles;
		posTrainArticles = new ArrayList<Article>();
		negTrainArticles = new ArrayList<Article>();

		for (int i = 0; i < posTrain.length; i++)
			posTrainArticles.add(new Article(posTrain[i], 1));
		for (int i = 0; i < negTrain.length; i++)
			negTrainArticles.add(new Article(negTrain[i], -1));

		mixPick = experiment(Ngram, topNgram, posTrainArticles,
				negTrainArticles, posPickSet, negPickSet, mixPickSet,
				mixPickPosMap, retainNgram);

		posTest = shuffleChooser.posTest
				.toArray(new String[shuffleChooser.posTest.size()]);
		negTest = shuffleChooser.negTest
				.toArray(new String[shuffleChooser.negTest.size()]);

		for (int i = 0; i < posTest.length; i++)
			posTrainArticles.add(new Article(posTest[i], 1));
		for (int i = 0; i < negTest.length; i++)
			negTrainArticles.add(new Article(negTest[i], -1));

		preprocessInput(Ngram, posTrainArticles, negTrainArticles, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		retraining(posTrainArticles, negTrainArticles);
		
		posTest = loader.testPos.toArray(new String[loader.testPos.size()]);
		negTest = loader.testNeg.toArray(new String[loader.testNeg.size()]);

		// posTest = posTrain;
		// negTest = negTrain;

		MeasureInfo performance;
		performance = processTestInput(Ngram, topNgram, posTest, negTest,
				mixPick, posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		System.out.printf("performance %f\n\n", performance.P * 100);
	}

	public static MeasureInfo processTestInput(int Ngram, int topNgram,
			String[] posTest, String[] negTest, ArrayList<nGram> mixPick,
			TreeSet<nGram> posPickSet, TreeSet<nGram> negPickSet,
			TreeSet<nGram> mixPickSet, TreeMap<nGram, Integer> mixPickPosMap) {
		ArrayList<Article> posTrainArticles2, negTrainArticles2;
		posTrainArticles2 = new ArrayList<Article>();
		negTrainArticles2 = new ArrayList<Article>();
		for (int i = 0; i < posTest.length; i++)
			posTrainArticles2.add(new Article(posTest[i], 1));
		for (int i = 0; i < negTest.length; i++)
			negTrainArticles2.add(new Article(negTest[i], -1));

		preprocessInput(Ngram, posTrainArticles2, negTrainArticles2, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		ArrayList<TreeMap<Integer, Double>> posVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec = new ArrayList<TreeMap<Integer, Double>>();
		for (Article pos : posTrainArticles2)
			posVec.add(pos.vec);
		for (Article neg : negTrainArticles2)
			negVec.add(neg.vec);
		stdout(String.format("\n# Test #\n\n"), 1);

		MeasureInfo best = new MeasureInfo(), worst = new MeasureInfo(), avg = new MeasureInfo(), tmp;
		best.P = 0;
		worst.P = 1;
		avg.P = 0;
		tmp = OutputClassifier.testLMClassifier(mixPick, LMmachine,
				posTrainArticles2, negTrainArticles2);
		best.P = Math.max(best.P, tmp.P);
		worst.P = Math.min(worst.P, tmp.P);
		avg.P += tmp.P;

		for (int i = 0; i < MLmachines.length; i++) {
			tmp = OutputClassifier.testClassifier("Winnow", MLmachines[i],
					posVec, negVec);
			best.P = Math.max(best.P, tmp.P);
			worst.P = Math.min(worst.P, tmp.P);
			avg.P += tmp.P;
		}

		for (int i = 0; i < PAmachines.length; i++) {
			tmp = OutputClassifier.testClassifier("Passive-Aggressive",
					PAmachines[0], posVec, negVec);
			best.P = Math.max(best.P, tmp.P);
			worst.P = Math.min(worst.P, tmp.P);
			avg.P += tmp.P;
		}

		tmp = OutputClassifier.testSimpleDecision(lv1DecisionTree,
				posTrainArticles2, negTrainArticles2);
		best.P = Math.max(best.P, tmp.P);
		worst.P = Math.min(worst.P, tmp.P);
		avg.P += tmp.P;
		tmp = OutputClassifier.testMeetingInterview(lv1DecisionTree,
				meetingMachine, LMmachine, MLmachines, PAmachines, Ngram,
				topNgram, posTrainArticles2, negTrainArticles2);
		best.P = Math.max(best.P, tmp.P);
		worst.P = Math.min(worst.P, tmp.P);
		avg.P += tmp.P;
		avg.P /= (3 + MLmachines.length + PAmachines.length);
		return best;
	}

	public static ArrayList<nGram> experiment(int Ngram, int topNgram,
			ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, TreeSet<nGram> posPickSet,
			TreeSet<nGram> negPickSet, TreeSet<nGram> mixPickSet,
			TreeMap<nGram, Integer> mixPickPosMap, TreeSet<nGram> retainNgram) {

		stdout(String.format("* positive sieve %d-grams building ...\n", Ngram),
				1);

		DataSieve posSieve = new DataSieve(Ngram, posTrain, negTrain);

		// posSieve.printBestNgram(20);
		// System.exit(0);

		stdout(String.format("* negative sieve %d-grams building ...\n", Ngram),
				1);

		DataSieve negSieve = new DataSieve(Ngram, negTrain, posTrain);

		// negSieve.printBestNgram(20);
		// System.exit(0);
		stdout(String.format("* positive #ngram %d\n", posSieve.ngramCount), 1);
		stdout(String.format("* negative #ngram %d\n", negSieve.ngramCount), 1);

		ArrayList<nGram> posPick, negPick, mixPick;

		posPick = posSieve.getBestNgram(topNgram / 2);
		negPick = negSieve.getBestNgram(topNgram / 2);

		PickTool pickTool = new PickTool(posPick, negPick, retainNgram);

		mixPick = pickTool.getMixPick(topNgram, posPickSet, negPickSet);

		for (int i = 0; i < mixPick.size(); i++) {
			mixPickPosMap.put(mixPick.get(i), i);
			mixPickSet.add(mixPick.get(i));
		}
		topNgram = mixPick.size();

		stdout(String.format("* actual #top-Ngrams %d\n", topNgram), 1);

		preprocessInput(Ngram, posTrainArticles, negTrainArticles, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		MLmachines = new WinnowMachine[2];
		PAmachines = new PassiveAggressive[2];

		stdout(String.format("\n## Simple Decision ##\n"), 1);
		lv1DecisionTree = createSimpleDecision(posTrainArticles,
				negTrainArticles);
		stdout(String.format("\n## Language Model ##\n"), 1);
		LMmachine = createLanguageModel(Ngram, posTrainArticles,
				negTrainArticles);
		stdout(String.format("\n## Winnow Algorithm ##\n"), 1);
		for (int i = 0; i < MLmachines.length; i++)
			MLmachines[i] = createWinnow(topNgram, posTrainArticles,
					negTrainArticles);
		stdout(String.format("\n## Passive-Aggressive Algorithm ##\n"), 1);
		for (int i = 0; i < PAmachines.length; i++)
			PAmachines[i] = createPassiveAggressive(topNgram, posTrainArticles,
					negTrainArticles);
		stdout(String.format("\n## Adaboost ##\n"), 1);
		meetingMachine = createMeeting(LMmachine, MLmachines, PAmachines,
				lv1DecisionTree, Ngram, topNgram, mixPick, posTrainArticles,
				negTrainArticles);
		return mixPick;
	}

	public static void preprocessInput(int Ngram,
			ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, ArrayList<nGram> mixPick,
			TreeSet<nGram> posPickSet, TreeSet<nGram> negPickSet,
			TreeSet<nGram> mixPickSet, TreeMap<nGram, Integer> mixPickPosMap) {
		int n = posTrainArticles.size();
		int m = negTrainArticles.size();
		stdout(String.format("\npreprocess %d pos, %d neg-items to vector\n",
				n, m), 1);
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
		lv1DecisionTree.training(posTrainArticles2, negTrainArticles2, 1);
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
			predictW = 0;
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
		meetingMachine.training(votePosVec, voteNegVec);
		return meetingMachine;
	}

	public static LanguageModel createLanguageModel(int Ngram,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		LanguageModel LMmachine = new LanguageModel(Ngram);
		LMmachine.training(posTrainArticles2, negTrainArticles2);
		return LMmachine;
	}

	public static WinnowMachine createWinnow(int topNgram,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		WinnowMachine MLmachine = new WinnowMachine(topNgram);
		MLmachine.training(posTrainArticles2, negTrainArticles2, 0);
		return MLmachine;
	}

	public static PassiveAggressive createPassiveAggressive(int topNgram,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		PassiveAggressive PAmachine = new PassiveAggressive(topNgram);
		PAmachine.training(posTrainArticles2, negTrainArticles2, 0);
		return PAmachine;
	}

	public static void retraining(ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		for (PassiveAggressive PAmachine : PAmachines) {
			PAmachine.training(posTrainArticles2, negTrainArticles2, 0);
		}
		for (WinnowMachine MLmachine : MLmachines) {
			MLmachine.training(posTrainArticles2, negTrainArticles2, 0);
		}
		lv1DecisionTree.training(posTrainArticles2, negTrainArticles2, 1);

		int base1 = 2, base2 = 2 + MLmachines.length;
		ArrayList<TreeMap<Integer, Double>> votePosVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> voteNegVec = new ArrayList<TreeMap<Integer, Double>>();
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
			predictW = 0;
			if (predict)
				voteVec.put(1 * 2, predictW);
			else
				voteVec.put(1 * 2 + 1, predictW);

			for (int j = 0; j < MLmachines.length; j++) {
				predict = MLmachines[j].classify(article.vec);
				predictW = MLmachines[j].strongClassify(article.vec);
				if (predict)
					voteVec.put((j + base1) * 2, predictW);
				else
					voteVec.put((j + base1) * 2 + 1, predictW);
			}

			for (int j = 0; j < PAmachines.length; j++) {
				predict = PAmachines[j].classify(article.vec);
				predictW = PAmachines[j].strongClassify(article.vec);
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
		meetingMachine.training(votePosVec, voteNegVec);
	}
}
