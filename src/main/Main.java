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
	public final static int ITLIMIT = 32;
	public static int stdout_status = 1;

	public static void main(String[] args) {
		// Dashboard demo = new Dashboard();
		int Ngram = 4, topNgram = 40000;
		String trainingPath = "training_set", testPath = "user_test";
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-n")) {
				try {
					int v = Integer.parseInt(args[i + 1]);
					Ngram = v;
				} catch (Exception e) {

				}
			}
			if (args[i].equals("-top")) {
				try {
					int v = Integer.parseInt(args[i + 1]);
					topNgram = v;
				} catch (Exception e) {

				}
			}
			if (args[i].equals("-path")) {
				try {
					String v = args[i + 1];
					trainingPath = v;
				} catch (Exception e) {

				}
			}
			if (args[i].equals("-tpath")) {
				try {
					String v = args[i + 1];
					testPath = v;
				} catch (Exception e) {

				}
			}
		}
		work(Ngram, topNgram, trainingPath, testPath);
	}

	public static void stdout(String s, int priority) {
		if (stdout_status == 1 && priority > 0)
			System.out.print(s);
	}

	public static void work(int Ngram, int topNgram, String trainingPath,
			String testPath) {
		loader = new Loader(trainingPath, testPath);
		ShuffleChooser shuffleChooser = new ShuffleChooser(loader.posViews,
				loader.negViews);
		outputPath = testPath + "/output";
		System.out.println("## Configuration ##\n");
		System.out.printf("* Ngram %d\n* topNgram %d\n", Ngram, topNgram);

		TreeMap<nGram, Double> ngramScoreMap = new TreeMap<nGram, Double>();
		TreeSet<nGram> posPickSet = null, negPickSet = null, mixPickSet = null;
		TreeMap<nGram, Integer> mixPickPosMap = null;
		ArrayList<nGram> mixPick = null;
		for (int cross = 0; cross < 10; cross++) { // 2-fold cross-validation
			shuffleChooser.shuffle(1, 1);
			ShuffleChooser split2 = new ShuffleChooser(shuffleChooser.posTrain,
					shuffleChooser.negTrain);
			split2.shuffle(1, 1);
			ArrayList<Article> posTrainArticles, negTrainArticles;
			ArrayList<Article> posTestArticles, negTestArticles;
			posTrainArticles = new ArrayList<Article>(split2.posTrain);
			negTrainArticles = new ArrayList<Article>(split2.negTrain);
			posTestArticles = new ArrayList<Article>(split2.posTest);
			negTestArticles = new ArrayList<Article>(split2.negTest);

			posPickSet = new TreeSet<nGram>();
			negPickSet = new TreeSet<nGram>();
			mixPickSet = new TreeSet<nGram>();
			mixPickPosMap = new TreeMap<nGram, Integer>();

			stdout(String.format("\n# Cross-validation Work %d #\n\n", cross),
					1);

			mixPick = experiment(Ngram, topNgram, posTrainArticles,
					negTrainArticles, posPickSet, negPickSet, mixPickSet,
					mixPickPosMap, null);

			MeasureInfo performance;
			performance = processTestInput(Ngram, posTrainArticles,
					negTrainArticles, posTestArticles, negTestArticles,
					mixPick, posPickSet, negPickSet, mixPickSet, mixPickPosMap);

			System.out.printf("performance %f\n\n", performance.P * 100);

			for (int i = 0; i < mixPick.size(); i++) {
				nGram e = mixPick.get(i);
				double score = performance.P, w = 1;
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
		int retainNgramSize = ngramScoreMap.size() / 4;

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

		// for (int i = 0, cnt = 0; cnt < 100 && i < ngramScoreArr.size(); i++)
		// {
		// nGram e = ngramScoreArr.get(i).getFirst();
		// if (e.getNonTerminal() == Ngram) {
		// cnt++;
		// System.out.printf("Score ( ");
		// for (int j = 0; j < e.iWord.length; j++) {
		// System.out.printf("%s ",
		// ModelUtilities.getWordName(e.iWord[j]));
		// }
		// System.out.println(")");
		// }
		// }

		TreeSet<nGram> posPickSet = null, negPickSet = null, mixPickSet = null;
		TreeMap<nGram, Integer> mixPickPosMap = null;
		ArrayList<nGram> mixPick = null;
		shuffleChooser.shuffle(3, 1);

		posPickSet = new TreeSet<nGram>();
		negPickSet = new TreeSet<nGram>();
		mixPickSet = new TreeSet<nGram>();
		mixPickPosMap = new TreeMap<nGram, Integer>();

		stdout(String.format("\n# Final Work #\n\n"), 1);

		ArrayList<Article> posTrainArticles, negTrainArticles;
		ArrayList<Article> posTestArticles, negTestArticles;

		posTrainArticles = new ArrayList<Article>(shuffleChooser.posTrain);
		negTrainArticles = new ArrayList<Article>(shuffleChooser.negTrain);
		posTestArticles = new ArrayList<Article>(shuffleChooser.posTest);
		negTestArticles = new ArrayList<Article>(shuffleChooser.negTest);

		mixPick = experiment(Ngram, topNgram, posTrainArticles,
				negTrainArticles, posPickSet, negPickSet, mixPickSet,
				mixPickPosMap, retainNgram);

		posTrainArticles.addAll(posTestArticles);
		negTrainArticles.addAll(negTestArticles);

		preprocessInput(Ngram, posTrainArticles, negTrainArticles, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		retraining(posTrainArticles, negTrainArticles);

		posTestArticles = new ArrayList<Article>(loader.testPos);
		negTestArticles = new ArrayList<Article>(loader.testNeg);

		// posTest = posTrain;
		// negTest = negTrain;

		MeasureInfo performance;
		performance = processTestInput(Ngram, posTrainArticles,
				negTrainArticles, posTestArticles, negTestArticles, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);
		System.out.printf("performance %f\n\n", performance.P * 100);
		onlineClassify(posTrainArticles, negTrainArticles, posTestArticles,
				negTestArticles);

	}

	public static MeasureInfo processTestInput(int Ngram,
			ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles,
			ArrayList<Article> posTestArticles,
			ArrayList<Article> negTestArticles, ArrayList<nGram> mixPick,
			TreeSet<nGram> posPickSet, TreeSet<nGram> negPickSet,
			TreeSet<nGram> mixPickSet, TreeMap<nGram, Integer> mixPickPosMap) {

		preprocessInput(Ngram, posTestArticles, negTestArticles, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		stdout(String.format("\n# Test #\n\n"), 1);

		MeasureInfo performance;
		performance = singleClassify(posTestArticles, negTestArticles);

		return performance;
	}

	public static MeasureInfo onlineClassify(
			ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles,
			ArrayList<Article> posTestArticles,
			ArrayList<Article> negTestArticles) {
		stdout(String.format("\n### Online Classify ###\n\n"), 1);
		int[][] tablePos, tableNeg, tableAll;
		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		ArrayList<Article> trainArticles = new ArrayList<Article>();
		ArrayList<Article> testArticles = new ArrayList<Article>();
		ArrayList<Article> group = new ArrayList<Article>();
		int groupTest = 50;
		testArticles.addAll(posTestArticles);
		testArticles.addAll(negTestArticles);
		trainArticles.addAll(posTrainArticles);
		trainArticles.addAll(negTrainArticles);
		Collections.shuffle(testArticles);

		for (int i = 0; i < testArticles.size(); i++) {
			if (group.size() < groupTest)
				group.add(testArticles.get(i));

			if (group.size() == groupTest || i == testArticles.size() - 1) {
				int n = group.size();
				TreeSet<Integer> appear = new TreeSet<Integer>();
				for (Article test : group) {
					for (Map.Entry<Integer, Double> entry : test.vec.entrySet()) {
						appear.add(entry.getKey());
					}
				}
				PassiveAggressive clonePA = PAmachines[0].clone();
				clonePA.setLimited(appear);
				for (int it = 0; it < ITLIMIT; it++) {
					Collections.shuffle(trainArticles);
					for (Article e : trainArticles) {
						if (e.polarity > 0) {
							clonePA.add(e.vec, 1);
						} else {
							clonePA.add(e.vec, -1);
						}
					}
				}

				for (Article e : group) {
					if (e.polarity > 0) {
						if (clonePA.classify(e.vec)) {
							tableNeg[0][0]++;
							tablePos[1][1]++;
						} else {
							tableNeg[0][1]++;
							tablePos[1][0]++;
						}
					} else {
						if (clonePA.classify(e.vec)) {
							tableNeg[1][0]++;
							tablePos[0][1]++;
						} else {
							tableNeg[1][1]++;
							tablePos[0][0]++;
						}
					}
				}
				group.clear();
			}
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];
		ReturnCell<MeasureInfo> posP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		ReturnCell<MeasureInfo> negP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		String algName = "Online training-back";
		OutputClassifier
				.printTable(algName + " Class Positive", tablePos, posP);
		OutputClassifier
				.printTable(algName + " Class Negative", tableNeg, negP);
		OutputClassifier.printTable(algName + " Final", tableAll);
		MeasureInfo avg = new MeasureInfo(posP.get(), negP.get());
		return avg;
	}

	public static MeasureInfo singleClassify(
			ArrayList<Article> posTestArticles2,
			ArrayList<Article> negTestArticles2) {
		MeasureInfo best = new MeasureInfo(), worst = new MeasureInfo(), avg = new MeasureInfo(), tmp;
		best.P = 0;
		worst.P = 1;
		avg.P = 0;
		tmp = OutputClassifier.testLMClassifier(LMmachine, posTestArticles2,
				negTestArticles2);
		best.P = Math.max(best.P, tmp.P);
		worst.P = Math.min(worst.P, tmp.P);
		avg.P += tmp.P;

		for (int i = 0; i < MLmachines.length; i++) {
			tmp = OutputClassifier.testClassifier("Winnow", MLmachines[i],
					posTestArticles2, negTestArticles2);
			best.P = Math.max(best.P, tmp.P);
			worst.P = Math.min(worst.P, tmp.P);
			avg.P += tmp.P;
		}

		for (int i = 0; i < PAmachines.length; i++) {
			tmp = OutputClassifier.testClassifier("Passive-Aggressive",
					PAmachines[0], posTestArticles2, negTestArticles2);
			best.P = Math.max(best.P, tmp.P);
			worst.P = Math.min(worst.P, tmp.P);
			avg.P += tmp.P;
		}

		// tmp = OutputClassifier.testSimpleDecision(lv1DecisionTree,
		// posTestArticles2, negTestArticles2);
		// best.P = Math.max(best.P, tmp.P);
		// worst.P = Math.min(worst.P, tmp.P);
		// avg.P += tmp.P;
		tmp = OutputClassifier.testMeetingInterview(lv1DecisionTree,
				meetingMachine, LMmachine, MLmachines, PAmachines,
				posTestArticles2, negTestArticles2);
		best.P = Math.max(best.P, tmp.P);
		worst.P = Math.min(worst.P, tmp.P);
		avg.P += tmp.P;
		avg.P /= (2 + MLmachines.length + PAmachines.length);
		return best;
	}

	public static ArrayList<nGram> experiment(int Ngram, int topNgram,
			ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, TreeSet<nGram> posPickSet,
			TreeSet<nGram> negPickSet, TreeSet<nGram> mixPickSet,
			TreeMap<nGram, Integer> mixPickPosMap, TreeSet<nGram> retainNgram) {

		stdout(String.format("* positive sieve %d-grams building ...\n", Ngram),
				1);

		DataSieve posSieve = new DataSieve(Ngram, posTrainArticles,
				negTrainArticles);

		// posSieve.printBestNgram(20);
		// System.exit(0);

		stdout(String.format("* negative sieve %d-grams building ...\n", Ngram),
				1);

		DataSieve negSieve = new DataSieve(Ngram, negTrainArticles,
				posTrainArticles);

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

		stdout(String.format("\n* Simple Decision"), 1);
		lv1DecisionTree = createSimpleDecision(posTrainArticles,
				negTrainArticles);
		stdout(String.format("\n* Language Model"), 1);
		LMmachine = createLanguageModel(Ngram, posTrainArticles,
				negTrainArticles);
		stdout(String.format("\n* Winnow Algorithm"), 1);
		for (int i = 0; i < MLmachines.length; i++)
			MLmachines[i] = createWinnow(topNgram, posTrainArticles,
					negTrainArticles);
		stdout(String.format("\n* Passive-Aggressive Algorithm"), 1);
		for (int i = 0; i < PAmachines.length; i++)
			PAmachines[i] = createPassiveAggressive(topNgram, posTrainArticles,
					negTrainArticles);
		stdout(String.format("\n* Adaboost"), 1);
		meetingMachine = createMeeting(LMmachine, MLmachines, PAmachines,
				lv1DecisionTree, Ngram, topNgram, mixPick, posTrainArticles,
				negTrainArticles);
		stdout(String.format("\n\n"), 1);
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
