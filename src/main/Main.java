package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import comp.MeasureInfo;
import comp.Pair;
import comp.ReturnCell;
import model.Article;
import model.DataSieve;
import model.nGram;
import model.classifier.LanguageModel;
import model.classifier.PassiveAggressive;
import model.classifier.WinnowMachine;

public class Main {
	public static int stdout_status = 1;
	public static TrainingConfig workConfig;

	public static void main(String[] args) {
		workConfig = new TrainingConfig(args);
		work(workConfig);
	}

	public static void stdout(String s, int priority) {
		if (stdout_status == 1 && priority > 0)
			System.out.print(s);
	}

	public static void work(TrainingConfig workConfig) {
		Loader loader;
		loader = new Loader(workConfig.trainingPath, workConfig.testPath);
		ShuffleChooser shuffleChooser = new ShuffleChooser(loader.posViews,
				loader.negViews);
		System.out.println("## Configuration ##\n");
		System.out.printf("* Ngram %d\n* topNgram %d\n", workConfig.Ngram,
				workConfig.topNgram);

		TreeMap<nGram, Double> ngramScoreMap = new TreeMap<nGram, Double>();
		TreeSet<nGram> posPickSet = null, negPickSet = null, mixPickSet = null;
		TreeMap<nGram, Integer> mixPickPosMap = null;
		ArrayList<nGram> mixPick = null;
		/**
		 * 2-fold cross-validation
		 */
		for (int cross = 0; cross < workConfig.cross; cross++) {
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

			mixPick = experiment(workConfig, workConfig.Ngram,
					workConfig.topNgram, posTrainArticles, negTrainArticles,
					posPickSet, negPickSet, mixPickSet, mixPickPosMap, null);

			MeasureInfo performance;

			performance = processTestInput(workConfig, workConfig.Ngram,
					posTrainArticles, negTrainArticles, posTestArticles,
					negTestArticles, mixPick, posPickSet, negPickSet,
					mixPickSet, mixPickPosMap);

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

		afterTraining(workConfig, loader, shuffleChooser, ngramScoreMap);
	}

	/**
	 * 
	 * @param workConfig
	 * @param loader
	 * @param shuffleChooser
	 * @param ngramScoreMap
	 */
	public static void afterTraining(TrainingConfig workConfig, Loader loader,
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

		shuffleChooser.shuffle(3, 1);

		TreeMap<nGram, Integer> mixPickPosMap;
		TreeSet<nGram> posPickSet, negPickSet, mixPickSet;
		ArrayList<nGram> mixPick;

		posPickSet = new TreeSet<nGram>();
		negPickSet = new TreeSet<nGram>();
		mixPickSet = new TreeSet<nGram>();
		mixPickPosMap = new TreeMap<nGram, Integer>();

		workConfig.setSupportDS(posPickSet, negPickSet, mixPickSet,
				mixPickPosMap);

		stdout(String.format("\n# Final Work #\n\n"), 1);

		ArrayList<Article> posTrainArticles, negTrainArticles;
		ArrayList<Article> posTestArticles, negTestArticles;

		posTrainArticles = new ArrayList<Article>(shuffleChooser.posTrain);
		negTrainArticles = new ArrayList<Article>(shuffleChooser.negTrain);
		posTestArticles = new ArrayList<Article>(shuffleChooser.posTest);
		negTestArticles = new ArrayList<Article>(shuffleChooser.negTest);

		workConfig.setTrainingData(posTrainArticles, negTrainArticles);

		mixPick = experiment(workConfig, workConfig.Ngram, workConfig.topNgram,
				posTrainArticles, negTrainArticles, posPickSet, negPickSet,
				mixPickSet, mixPickPosMap, retainNgram);

		workConfig.mixPick = mixPick;

		posTrainArticles.addAll(posTestArticles);
		negTrainArticles.addAll(negTestArticles);

		preprocessInput(workConfig.Ngram, posTrainArticles, negTrainArticles,
				mixPick, posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		retraining(workConfig, posTrainArticles, negTrainArticles);

		posTestArticles = new ArrayList<Article>(loader.testPos);
		negTestArticles = new ArrayList<Article>(loader.testNeg);

		// posTest = posTrain;
		// negTest = negTrain;

		MeasureInfo performance;
		performance = processTestInput(workConfig, workConfig.Ngram,
				posTrainArticles, negTrainArticles, posTestArticles,
				negTestArticles, mixPick, posPickSet, negPickSet, mixPickSet,
				mixPickPosMap);
		System.out.printf("performance %f\n\n", performance.P * 100);
		onlineClassify(workConfig, posTrainArticles, negTrainArticles,
				posTestArticles, negTestArticles, null);

	}

	public static MeasureInfo processTestInput(TrainingConfig workConfig,
			int Ngram, ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles,
			ArrayList<Article> posTestArticles,
			ArrayList<Article> negTestArticles, ArrayList<nGram> mixPick,
			TreeSet<nGram> posPickSet, TreeSet<nGram> negPickSet,
			TreeSet<nGram> mixPickSet, TreeMap<nGram, Integer> mixPickPosMap) {

		preprocessInput(Ngram, posTestArticles, negTestArticles, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		stdout(String.format("\n# Test #\n\n"), 1);

		MeasureInfo performance;
		performance = singleClassify(workConfig, posTestArticles,
				negTestArticles);

		return performance;
	}

	public static MeasureInfo onlineClassify(TrainingConfig workConfig,
			ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles,
			ArrayList<Article> posTestArticles,
			ArrayList<Article> negTestArticles, ReturnCell<PassiveAggressive> PA) {
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
				TreeSet<Integer> appear = new TreeSet<Integer>();
				for (Article test : group) {
					for (Map.Entry<Integer, Double> entry : test.vec.entrySet()) {
						appear.add(entry.getKey());
					}
				}
				for (int j = 0; j < 5 && j < posTrainArticles.size(); j++) {
					int x = (int) (Math.random() * posTrainArticles.size());
					Article test = posTrainArticles.get(x);
					for (Map.Entry<Integer, Double> entry : test.vec.entrySet()) {
						appear.add(entry.getKey());
					}
				}
				for (int j = 0; j < 5 && j < negTrainArticles.size(); j++) {
					int x = (int) (Math.random() * negTrainArticles.size());
					Article test = negTrainArticles.get(x);
					for (Map.Entry<Integer, Double> entry : test.vec.entrySet()) {
						appear.add(entry.getKey());
					}
				}
				PassiveAggressive clonePA = workConfig.PAmachines[0].clone();
				clonePA.setLimited(appear);
				for (int it = 0; it < workConfig.ONLINE_ITLIMIT; it++) {
					Collections.shuffle(trainArticles);
					for (Article e : trainArticles) {
						if (e.polarity > 0) {
							clonePA.add(e.vec, 1);
						} else {
							clonePA.add(e.vec, -1);
						}
					}
				}
				if (PA != null)
					PA.set(clonePA);
				for (Article e : group) {
					if (e.polarity > 0) {
						if (clonePA.classify(e.vec)) {
							tableNeg[0][0]++;
							tablePos[1][1]++;
							e.predict_polarity = 1;
						} else {
							tableNeg[0][1]++;
							tablePos[1][0]++;
							e.predict_polarity = -1;
						}
					} else {
						if (clonePA.classify(e.vec)) {
							tableNeg[1][0]++;
							tablePos[0][1]++;
							e.predict_polarity = 1;
						} else {
							tableNeg[1][1]++;
							tablePos[0][0]++;
							e.predict_polarity = -1;
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
		OutputClassifier.storeOutputFile(algName, posTestArticles,
				negTestArticles);
		MeasureInfo avg = new MeasureInfo(posP.get(), negP.get());
		return avg;
	}

	public static MeasureInfo singleClassify(TrainingConfig workConfig,
			ArrayList<Article> posTestArticles2,
			ArrayList<Article> negTestArticles2) {
		MeasureInfo best = new MeasureInfo(MeasureInfo.MIN);
		MeasureInfo worst = new MeasureInfo(MeasureInfo.MAX);
		MeasureInfo avg = new MeasureInfo(MeasureInfo.MIN), tmp;

		tmp = OutputClassifier.testLMClassifier(workConfig.LMmachine,
				posTestArticles2, negTestArticles2);
		best = best.max(tmp);
		worst = worst.min(tmp);
		avg = avg.add(tmp);

		for (int i = 0; i < workConfig.MLmachines.length; i++) {
			tmp = OutputClassifier.testClassifier("Winnow",
					workConfig.MLmachines[i], posTestArticles2,
					negTestArticles2);
			best = best.max(tmp);
			worst = worst.min(tmp);
			avg = avg.add(tmp);
		}
		for (int i = 0; i < workConfig.PAmachines.length; i++) {
			tmp = OutputClassifier.testClassifier("Passive-Aggressive",
					workConfig.PAmachines[i], posTestArticles2,
					negTestArticles2);
			best = best.max(tmp);
			worst = worst.min(tmp);
			avg = avg.add(tmp);
		}
		tmp = OutputClassifier.testMeetingInterview(workConfig.meetingMachine,
				workConfig.LMmachine, workConfig.MLmachines,
				workConfig.PAmachines, posTestArticles2, negTestArticles2);
		best = best.max(tmp);
		worst = worst.min(tmp);
		avg = avg.add(tmp);
		avg.P /= (1 + workConfig.MLmachines.length + workConfig.PAmachines.length);
		return best;
	}

	public static ArrayList<nGram> experiment(TrainingConfig workConfig,
			int Ngram, int topNgram, ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, TreeSet<nGram> posPickSet,
			TreeSet<nGram> negPickSet, TreeSet<nGram> mixPickSet,
			TreeMap<nGram, Integer> mixPickPosMap, TreeSet<nGram> retainNgram) {

		stdout(String.format("* positive sieve %d-grams building ...\n", Ngram),
				1);

		DataSieve posSieve = new DataSieve(Ngram, posTrainArticles,
				negTrainArticles);

		stdout(String.format("* negative sieve %d-grams building ...\n", Ngram),
				1);

		DataSieve negSieve = new DataSieve(Ngram, negTrainArticles,
				posTrainArticles);

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

		mixPickPosMap.put(nGram.POS, mixPick.size());
		mixPickSet.add(nGram.POS);
		mixPickPosMap.put(nGram.NEG, mixPick.size() + 1);
		mixPickSet.add(nGram.NEG);

		topNgram = mixPick.size();

		stdout(String.format("* actual #top-Ngrams %d\n", topNgram), 1);

		preprocessInput(Ngram, posTrainArticles, negTrainArticles, mixPick,
				posPickSet, negPickSet, mixPickSet, mixPickPosMap);

		workConfig.MLmachines = new WinnowMachine[2];
		workConfig.PAmachines = new PassiveAggressive[2];

		stdout(String.format("\n* Language Model"), 1);
		workConfig.LMmachine = createLanguageModel(Ngram, posTrainArticles,
				negTrainArticles);
		stdout(String.format("\n* Winnow Algorithm"), 1);
		for (int i = 0; i < workConfig.MLmachines.length; i++)
			workConfig.MLmachines[i] = createWinnow(topNgram, posTrainArticles,
					negTrainArticles);
		stdout(String.format("\n* Passive-Aggressive Algorithm"), 1);
		for (int i = 0; i < workConfig.PAmachines.length; i++)
			workConfig.PAmachines[i] = createPassiveAggressive(topNgram,
					posTrainArticles, negTrainArticles);
		stdout(String.format("\n* Adaboost"), 1);
		workConfig.meetingMachine = createMeeting(workConfig.LMmachine,
				workConfig.MLmachines, workConfig.PAmachines, Ngram, topNgram,
				mixPick, posTrainArticles, negTrainArticles);
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

	public static PassiveAggressive createMeeting(LanguageModel LMmachine,
			WinnowMachine[] MLmachine2, PassiveAggressive[] PAmachine2,
			int Ngram, int topNgram, ArrayList<nGram> mixPick,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		int vectorSize = (1 + MLmachine2.length + PAmachine2.length) * 2;
		int base1 = 1, base2 = 1 + MLmachine2.length;
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

	/**
	 * void overfitting, need retraining by another data.
	 * 
	 * @param workConfig
	 * @param posTrainArticles2
	 * @param negTrainArticles2
	 */
	public static void retraining(TrainingConfig workConfig,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		for (PassiveAggressive PAmachine : workConfig.PAmachines) {
			PAmachine.training(posTrainArticles2, negTrainArticles2, 0);
		}
		for (WinnowMachine MLmachine : workConfig.MLmachines) {
			MLmachine.training(posTrainArticles2, negTrainArticles2, 0);
		}

		int base1 = 1, base2 = 1 + workConfig.MLmachines.length;
		ArrayList<TreeMap<Integer, Double>> votePosVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> voteNegVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTrainArticles2);
		articles.addAll(negTrainArticles2);

		for (Article article : articles) {
			double predictW;
			boolean predict;
			TreeMap<Integer, Double> voteVec = new TreeMap<Integer, Double>();

			predict = workConfig.LMmachine.classify(article.occGramVec);
			predictW = 1;
			if (predict)
				voteVec.put(0 * 2, predictW);
			else
				voteVec.put(0 * 2 + 1, predictW);

			for (int j = 0; j < workConfig.MLmachines.length; j++) {
				predict = workConfig.MLmachines[j].classify(article.vec);
				predictW = workConfig.MLmachines[j].strongClassify(article.vec);
				if (predict)
					voteVec.put((j + base1) * 2, predictW);
				else
					voteVec.put((j + base1) * 2 + 1, predictW);
			}

			for (int j = 0; j < workConfig.PAmachines.length; j++) {
				predict = workConfig.PAmachines[j].classify(article.vec);
				predictW = workConfig.PAmachines[j].strongClassify(article.vec);
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
		workConfig.meetingMachine.training(votePosVec, voteNegVec);
	}
}
