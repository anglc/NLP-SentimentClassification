package main;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import model.Classifier;
import model.DecisionStump;
import model.LanguageModel;
import model.ModelUtilities;
import model.PassiveAggressive;
import model.WinnowMachine;
import model.nGram;

public class OutputClassifier {
	public static void testMeetingInterview(DecisionStump lv1DecisionTree,
			PassiveAggressive meetingMachine, LanguageModel LMmachine,
			WinnowMachine[] MLmachine, PassiveAggressive[] PAmachine,
			int Ngram, int topNgram, ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		int vectorSize = (2 + MLmachine.length + PAmachine.length) * 2;
		int base1 = 2, base2 = 2 + MLmachine.length;
		ArrayList<TreeMap<Integer, Double>> posVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec = new ArrayList<TreeMap<Integer, Double>>();

		System.out.printf("* Meeting prepare ...\n");
		System.out.printf("* Meeting top-%d testing ...\n\n", topNgram);

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
			
			for (int j = 0; j < MLmachine.length; j++) {
				predict = MLmachine[j].classify(article.vec);
				predictW = MLmachine[j].strongClassify(article.vec);
				if (predict)
					voteVec.put((j + base1) * 2, predictW);
				else
					voteVec.put((j + base1) * 2 + 1, predictW);
			}
			
			for (int j = 0; j < PAmachine.length; j++) {
				predict = PAmachine[j].classify(article.vec);
				predictW = PAmachine[j].strongClassify(article.vec);
				if (predict)
					voteVec.put((j + base2) * 2, predictW);
				else
					voteVec.put((j + base2) * 2 + 1, predictW);
			}

			if (article.polarity > 0)
				posVec.add(voteVec);
			else
				negVec.add(voteVec);

		}

		testClassifier("Adaboost", meetingMachine, posVec, negVec);
		return;
	}

	public static void testSimpleDecision(DecisionStump lv1DecisionTree,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		ArrayList<TreeMap<Integer, Double>> posVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec = new ArrayList<TreeMap<Integer, Double>>();
		for (Article pos : posTrainArticles2)
			posVec.add(pos.occVec);
		for (Article neg : negTrainArticles2)
			negVec.add(neg.occVec);
		testClassifier("Simple Decision", lv1DecisionTree, posVec, negVec);
	}

	public static void testClassifier(String algName, Classifier classifier,
			ArrayList<TreeMap<Integer, Double>> posVec,
			ArrayList<TreeMap<Integer, Double>> negVec) {
		ArrayList<Integer> posOutput, negOutput, unkOutput;
		int[][] tablePos, tableNeg, tableAll;

		posOutput = new ArrayList<Integer>();
		negOutput = new ArrayList<Integer>();
		unkOutput = new ArrayList<Integer>();

		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (TreeMap<Integer, Double> pos : posVec) {
			if (classifier.classify(pos)) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
				posOutput.add(1);
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
				posOutput.add(0);
			}
		}
		for (TreeMap<Integer, Double> neg : negVec) {
			if (classifier.classify(neg)) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
				negOutput.add(1);
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
				negOutput.add(0);
			}
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		ModelUtilities.printTable(algName + " Class Positive", tablePos);
		ModelUtilities.printTable(algName + " Class Negative", tableNeg);
		ModelUtilities.printTable(algName + " Final", tableAll);
		storeOutputFile(algName, posOutput, negOutput);
	}

	public static void testLMClassifier(ArrayList<nGram> mixPick,
			LanguageModel LMmachine, ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		ArrayList<Integer> posOutput, negOutput, unkOutput;
		int[][] tablePos, tableNeg, tableAll;

		posOutput = new ArrayList<Integer>();
		negOutput = new ArrayList<Integer>();
		unkOutput = new ArrayList<Integer>();

		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (Article pos : posTrainArticles2) {
			if (LMmachine.classify(pos.occGramVec)) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
				posOutput.add(1);
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
				posOutput.add(0);
			}
		}
		for (Article neg : negTrainArticles2) {
			if (LMmachine.classify(neg.occGramVec)) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
				negOutput.add(1);
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
				negOutput.add(0);
			}
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		String algName = "Language Model";
		ModelUtilities.printTable(algName + " Class Positive", tablePos);
		ModelUtilities.printTable(algName + " Class Negative", tableNeg);
		ModelUtilities.printTable(algName + " Final", tableAll);
		storeOutputFile(algName, posOutput, negOutput);
	}

	public static void storeOutputFile(String algName,
			ArrayList<Integer> posOutput, ArrayList<Integer> negOutput) {
		try {
			File A, B, C;
			PrintWriter printWriter;
			A = new File(Main.outputPath + "/pos/" + algName + ".txt");
			B = new File(Main.outputPath + "/neg/" + algName + ".txt");
			C = new File(Main.outputPath + "/unknown/" + algName + ".txt");
			A.getParentFile().mkdirs();
			B.getParentFile().mkdirs();
			C.getParentFile().mkdirs();
			printWriter = new PrintWriter(A);
			for (int i = 0; i < posOutput.size(); i++) {
				String line = "";
				if (posOutput.get(i) == 1)
					line += Main.loader.testPosName.get(i) + " pos";
				else
					line += Main.loader.testPosName.get(i) + " neg";
				printWriter.println(line);
			}
			printWriter.close();
			printWriter = new PrintWriter(B);
			for (int i = 0; i < negOutput.size(); i++) {
				String line = "";
				if (negOutput.get(i) == 1)
					line += Main.loader.testNegName.get(i) + " pos";
				else
					line += Main.loader.testNegName.get(i) + " neg";
				printWriter.println(line);
			}
			printWriter.close();
		} catch (Exception e) {

		}
	}
}
