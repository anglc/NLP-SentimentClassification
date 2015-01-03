package main;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Classifier;
import model.LanguageModel;
import model.ModelUtilities;
import model.PassiveAggressive;
import model.WinnowMachine;
import model.nGram;

public class OutputClassifier {
	public static void testMeetingInterview(PassiveAggressive meetingMachine,
			LanguageModel LMmachine, WinnowMachine MLmachine,
			PassiveAggressive PAmachine, int Ngram, int topNgram,
			ArrayList<nGram> mixPick, String[] posTest, String[] negTest,
			String[] unkTest, ArrayList<double[]> posVec2,
			ArrayList<double[]> negVec2, ArrayList<double[]> unkVec2) {
		ArrayList<double[]> posVec = new ArrayList<double[]>();
		ArrayList<double[]> negVec = new ArrayList<double[]>();
		ArrayList<double[]> unkVec = new ArrayList<double[]>();

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
		for (int i = 0; i < unkTest.length; i++) {
			String views = null;
			double[] viewsVec = null;
			views = unkTest[i];
			viewsVec = unkVec2.get(i);

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
			unkVec.add(voteArr);
		}
		testClassifier("Adaboost", meetingMachine, posVec, negVec, unkVec);
		return;
	}

	public static void testClassifier(String algName, Classifier classifier,
			ArrayList<double[]> posVec, ArrayList<double[]> negVec,
			ArrayList<double[]> unkVec) {
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
		for (int i = 0; i < unkVec.size(); i++) {
			if (classifier.classify(unkVec.get(i))) {
				unkOutput.add(1);
			} else {
				unkOutput.add(0);
			}
		}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		ModelUtilities.printTable(algName + " Class Positive", tablePos);
		ModelUtilities.printTable(algName + " Class Negative", tableNeg);
		ModelUtilities.printTable(algName + " Final", tableAll);
		storeOutputFile(algName, posOutput, negOutput, unkOutput);
	}

	public static void testLMClassifier(ArrayList<nGram> mixPick,
			LanguageModel LMmachine, String[] posTest, String[] negTest,
			String[] unkTest) {
		ArrayList<Integer> posOutput, negOutput, unkOutput;
		int[][] tablePos, tableNeg, tableAll;

		posOutput = new ArrayList<Integer>();
		negOutput = new ArrayList<Integer>();
		unkOutput = new ArrayList<Integer>();

		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (String pos : posTest) {
			if (LMmachine.classify(pos, mixPick)) {
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
			if (LMmachine.classify(neg, mixPick)) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
				negOutput.add(1);
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
				negOutput.add(0);
			}
		}
		for (String unk : unkTest) {
			if (LMmachine.classify(unk, mixPick)) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
				unkOutput.add(1);
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
				unkOutput.add(0);
			}
		}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		String algName = "Language Model";
		ModelUtilities.printTable(algName + " Class Positive", tablePos);
		ModelUtilities.printTable(algName + " Class Negative", tableNeg);
		ModelUtilities.printTable(algName + " Final", tableAll);
		storeOutputFile(algName, posOutput, negOutput, unkOutput);
	}

	public static void storeOutputFile(String algName, ArrayList<Integer> posOutput,
			ArrayList<Integer> negOutput, ArrayList<Integer> unkOutput) {
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
					line += "1 " + Main.loader.testPosName.get(i);
				else
					line += "0 " + Main.loader.testPosName.get(i);
				printWriter.println(line);
			}
			printWriter.close();
			printWriter = new PrintWriter(B);
			for (int i = 0; i < negOutput.size(); i++) {
				String line = "";
				if (negOutput.get(i) == 1)
					line += "1 " + Main.loader.testNegName.get(i);
				else
					line += "0 " + Main.loader.testNegName.get(i);
				printWriter.println(line);
			}
			printWriter.close();
			printWriter = new PrintWriter(C);
			for (int i = 0; i < unkOutput.size(); i++) {
				String line = "";
				if (unkOutput.get(i) == 1)
					line += "1 " + Main.loader.testUnknownName.get(i);
				else
					line += "0 " + Main.loader.testUnknownName.get(i);
				printWriter.println(line);
			}
			printWriter.close();
		} catch (Exception e) {

		}
	}
}
