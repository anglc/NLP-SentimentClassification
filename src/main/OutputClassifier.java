package main;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;

import model.Classifier;
import model.DecisionStump;
import model.LanguageModel;
import model.PassiveAggressive;
import model.WinnowMachine;

public class OutputClassifier {
	public static int stdout_status = 1;
	public static int store_status = 0;

	public static void stdout(String s, int priority) {
		if (stdout_status == 1 && priority > 0)
			System.out.print(s);
	}

	public static MeasureInfo testMeetingInterview(
			DecisionStump lv1DecisionTree, PassiveAggressive meetingMachine,
			LanguageModel LMmachine, WinnowMachine[] MLmachine,
			PassiveAggressive[] PAmachine, ArrayList<Article> posTestArticles,
			ArrayList<Article> negTestArticles) {
		// int vectorSize = (2 + MLmachine.length + PAmachine.length) * 2;
		int base1 = 2, base2 = 2 + MLmachine.length;
		ArrayList<TreeMap<Integer, Double>> posVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec = new ArrayList<TreeMap<Integer, Double>>();

		ArrayList<Article> articles = new ArrayList<Article>();
		articles.addAll(posTestArticles);
		articles.addAll(negTestArticles);

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

		return testClassifierByVec("Adaboost", meetingMachine, posVec, negVec,
				posTestArticles, negTestArticles);
	}

	public static MeasureInfo testSimpleDecision(DecisionStump lv1DecisionTree,
			ArrayList<Article> posTestArticles,
			ArrayList<Article> negTestArticles) {
		ArrayList<TreeMap<Integer, Double>> posVec = new ArrayList<TreeMap<Integer, Double>>();
		ArrayList<TreeMap<Integer, Double>> negVec = new ArrayList<TreeMap<Integer, Double>>();
		for (Article pos : posTestArticles)
			posVec.add(pos.occVec);
		for (Article neg : negTestArticles)
			negVec.add(neg.occVec);
		return testClassifierByVec("Simple Decision", lv1DecisionTree, posVec,
				negVec, posTestArticles, negTestArticles);
	}

	public static MeasureInfo testClassifierByVec(String algName,
			Classifier classifier,
			ArrayList<TreeMap<Integer, Double>> posTestVec,
			ArrayList<TreeMap<Integer, Double>> negTestVec,
			ArrayList<Article> posTestArticles,
			ArrayList<Article> negTestArticles) {
		int[][] tablePos, tableNeg, tableAll;
		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (int i = 0; i < posTestArticles.size(); i++) {
			if (classifier.classify(posTestVec.get(i))) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
				posTestArticles.get(i).predict_polarity = 1;
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
				posTestArticles.get(i).predict_polarity = 0;
			}
		}
		for (int i = 0; i < negTestArticles.size(); i++) {
			if (classifier.classify(negTestVec.get(i))) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
				negTestArticles.get(i).predict_polarity = 1;
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
				negTestArticles.get(i).predict_polarity = 0;
			}
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		ReturnCell<MeasureInfo> posP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		ReturnCell<MeasureInfo> negP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		printTable(algName + " Class Positive", tablePos, posP);
		printTable(algName + " Class Negative", tableNeg, negP);
		printTable(algName + " Final", tableAll, null);
		storeOutputFile(algName, posTestArticles, negTestArticles);
		MeasureInfo avg = new MeasureInfo(posP.get(), negP.get());
		return avg;
	}

	public static MeasureInfo testClassifier(String algName,
			Classifier classifier, ArrayList<Article> posTestArticles2,
			ArrayList<Article> negTestArticles2) {
		int[][] tablePos, tableNeg, tableAll;
		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (Article pos : posTestArticles2) {
			if (classifier.classify(pos.vec)) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
				pos.predict_polarity = 1;
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
				pos.predict_polarity = 0;
			}
		}
		for (Article neg : negTestArticles2) {
			if (classifier.classify(neg.vec)) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
				neg.predict_polarity = 1;
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
				neg.predict_polarity = 0;
			}
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		ReturnCell<MeasureInfo> posP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		ReturnCell<MeasureInfo> negP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		printTable(algName + " Class Positive", tablePos, posP);
		printTable(algName + " Class Negative", tableNeg, negP);
		printTable(algName + " Final", tableAll, null);
		storeOutputFile(algName, posTestArticles2, negTestArticles2);
		MeasureInfo avg = new MeasureInfo(posP.get(), negP.get());
		return avg;
	}

	public static MeasureInfo testLMClassifier(LanguageModel LMmachine,
			ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		int[][] tablePos, tableNeg, tableAll;
		tablePos = new int[2][2];
		tableNeg = new int[2][2];
		tableAll = new int[2][2];
		for (Article pos : posTrainArticles2) {
			if (LMmachine.classify(pos.occGramVec)) {
				tableNeg[0][0]++;
				tablePos[1][1]++;
				pos.predict_polarity = 1;
			} else {
				tableNeg[0][1]++;
				tablePos[1][0]++;
				pos.predict_polarity = 0;
			}
		}
		for (Article neg : negTrainArticles2) {
			if (LMmachine.classify(neg.occGramVec)) {
				tableNeg[1][0]++;
				tablePos[0][1]++;
				neg.predict_polarity = 1;
			} else {
				tableNeg[1][1]++;
				tablePos[0][0]++;
				neg.predict_polarity = 0;
			}
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				tableAll[i][j] = tableNeg[i][j] + tablePos[i][j];

		ReturnCell<MeasureInfo> posP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		ReturnCell<MeasureInfo> negP = new ReturnCell<MeasureInfo>(
				new MeasureInfo());
		String algName = "Language Model";
		printTable(algName + " Class Positive", tablePos, posP);
		printTable(algName + " Class Negative", tableNeg, negP);
		printTable(algName + " Final", tableAll);
		storeOutputFile(algName, posTrainArticles2, negTrainArticles2);

		MeasureInfo avg = new MeasureInfo(posP.get(), negP.get());
		return avg;
	}

	public static void storeOutputFile(String algName,
			ArrayList<Article> posOutput, ArrayList<Article> negOutput) {

		try {
			File A, B;
			PrintWriter printWriter;
			A = new File(Main.outputPath + "/pos/" + algName + ".txt");
			B = new File(Main.outputPath + "/neg/" + algName + ".txt");
			A.getParentFile().mkdirs();
			B.getParentFile().mkdirs();
			printWriter = new PrintWriter(A);
			for (Article pos : posOutput) {
				String line = "";
				if (pos.predict_polarity == 1)
					line += pos.fileName + " pos";
				else
					line += pos.fileName + " neg";
				printWriter.println(line);
			}
			printWriter.close();
			printWriter = new PrintWriter(B);
			for (Article neg : negOutput) {
				String line = "";
				if (neg.predict_polarity == 1)
					line += neg.fileName + " pos";
				else
					line += neg.fileName + " neg";
				printWriter.println(line);
			}
			printWriter.close();
		} catch (Exception e) {

		}
	}

	public static void printTable(String tableName, int table[][],
			ReturnCell<MeasureInfo> retInfo) {
		stdout(String.format("Table `%s`\n", tableName), 1);
		stdout(String.format("\n|%16s|%15s|%15s|\n", "Truth\\Classifier",
				"Classifier no", "Classifier yes"), 0);
		stdout(String.format("|%16s|%15s|%15s|\n", "----------------",
				"---------------", "---------------"), 0);
		stdout(String.format("|%16s|%15d|%15d|\n", "Truth no", table[0][0],
				table[0][1]), 0);
		stdout(String.format("|%16s|%15d|%15d|\n", "Truth yes", table[1][0],
				table[1][1]), 0);

		double P, R, F1, beta = 1;
		P = (double) table[1][1] / (table[1][0] + table[1][1]);
		R = (double) table[1][1] / (table[0][1] + table[1][1]);
		F1 = (beta * beta + 1) * P * R / (beta * beta * P + R);

		stdout(String.format("\nP  %.3f %%, R  %.3f %%, F1  %.3f %%\n\n",
				P * 100, R * 100, F1 * 100), 1);
		if (retInfo != null)
			retInfo.set(new MeasureInfo(P, R));
	}

	public static void printTable(String tableName, int table[][]) {
		printTable(tableName, table, null);
	}
}
