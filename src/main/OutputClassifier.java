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
			WinnowMachine MLmachine, PassiveAggressive PAmachine, int Ngram,
			int topNgram, String[] posTest, String[] negTest,
			ArrayList<TreeMap<Integer, Double>> posVec2,
			ArrayList<TreeMap<Integer, Double>> negVec2,
			ArrayList<TreeMap<Integer, Double>> posOccVec2,
			ArrayList<TreeMap<Integer, Double>> negOccVec2) {
		ArrayList<TreeMap<Integer, Double>> posVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec = new ArrayList<TreeMap<Integer, Double>>();

		System.out.printf("* Meeting prepare ...\n");
		System.out.printf("* Meeting top-%d testing ...\n\n", topNgram);

		int posIdx = 0, negIdx = 0;
		boolean[] predict = new boolean[4];
		double[] predictWeight = new double[4];

		while (posIdx < posTest.length || negIdx < negTest.length) {
			boolean correct = false;
			String views = null;
			TreeMap<Integer, Double> viewsVec = null, voteVec = new TreeMap<Integer, Double>(), viewsOcc = null;
			double[] voteArr = new double[8];
			if ((Math.random() < 0.5 && posIdx < posTest.length)
					|| (negIdx >= negTest.length)) {
				views = posTest[posIdx];
				viewsVec = posVec2.get(posIdx);
				viewsOcc = posOccVec2.get(posIdx);
				posIdx++;
				correct = true;
			} else if (negIdx < negTest.length) {
				views = negTest[negIdx];
				viewsVec = negVec2.get(negIdx);
				viewsOcc = negOccVec2.get(negIdx);
				negIdx++;
				correct = false;
			}

			predict[0] = LMmachine.classify(views);
			predict[1] = MLmachine.classify(viewsVec);
			predict[2] = PAmachine.classify(viewsVec);
			predict[3] = lv1DecisionTree.classify(viewsOcc);

			predictWeight[0] = LMmachine.strongClassify(views);
			predictWeight[1] = MLmachine.strongClassify(viewsVec);
			predictWeight[2] = PAmachine.strongClassify(viewsVec);
			predictWeight[3] = lv1DecisionTree.strongClassify(viewsOcc);
			predictWeight[0] = 1;
			predictWeight[3] = 0;
			
//			voteArr[0] = LMmachine.strongClassify(views, "pos");
//			voteArr[4] = -LMmachine.strongClassify(views, "neg");
			for (int j = 0; j < 4; j++) {
				if (predict[j])
					voteArr[j] = predictWeight[j];
				else
					voteArr[j] = -predictWeight[j];
			}
			for (int j = 0; j < 8; j++) {
				voteVec.put(j, voteArr[j]);
			}
			if (correct)
				posVec.add(voteVec);
			else
				negVec.add(voteVec);
		}

		testClassifier("Adaboost", meetingMachine, posVec, negVec);
		return;
	}

	public static void testSimpleDecision(DecisionStump lv1DecisionTree,
			ArrayList<TreeMap<Integer, Double>> posVec2,
			ArrayList<TreeMap<Integer, Double>> negVec2,
			ArrayList<TreeMap<Integer, Double>> posOccVec2,
			ArrayList<TreeMap<Integer, Double>> negOccVec2) {
		ArrayList<TreeMap<Integer, Double>> votePosVec = posOccVec2;
		ArrayList<TreeMap<Integer, Double>> voteNegVec = negOccVec2;
		testClassifier("Simple Decision", lv1DecisionTree, votePosVec,
				voteNegVec);
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
		for (int i = 0; i < posVec.size(); i++) {
			if (classifier.classify(posVec.get(i))) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
				posOutput.add(1);
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
				posOutput.add(0);
			}
		}
		for (int i = 0; i < negVec.size(); i++) {
			if (classifier.classify(negVec.get(i))) {
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
			LanguageModel LMmachine, String[] posTest, String[] negTest) {
		ArrayList<Integer> posOutput, negOutput, unkOutput;
		int[][] tablePos, tableNeg, tableAll;

		posOutput = new ArrayList<Integer>();
		negOutput = new ArrayList<Integer>();
		unkOutput = new ArrayList<Integer>();

		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (String pos : posTest) {
			if (LMmachine.classify(pos)) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
				posOutput.add(1);
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
				posOutput.add(0);
			}
		}
		for (String neg : negTest) {
			if (LMmachine.classify(neg)) {
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
