package main;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import model.Article;
import model.ModelUtilities;
import model.nGram;
import model.classifier.DecisionStump;
import model.classifier.LanguageModel;
import model.classifier.PassiveAggressive;
import model.classifier.WinnowMachine;

public class TrainingConfig {
	/**
	 * Classifier
	 */
	public LanguageModel LMmachine;
	public WinnowMachine MLmachines[];
	public PassiveAggressive PAmachines[], meetingMachine;

	/**
	 * support transfer data structure
	 */
	public TreeMap<nGram, Integer> mixPickPosMap;
	public TreeSet<nGram> posPickSet, negPickSet, mixPickSet;
	public ArrayList<nGram> mixPick;

	/**
	 * prepare work configuration
	 */
	public int Ngram = 3, topNgram = 40000;
	public String trainingPath = "training_set", testPath = "user_test";
	public String outputPath;
	public int ITLIMIT = 20, ONLINE_ITLIMIT = 20;
	public int cross = 10;

	/**
	 * build in user training
	 */
	public ArrayList<Article> posTrainArticles, negTrainArticles;

	/**
	 * README.md args format.
	 * 
	 * @param args
	 */
	public TrainingConfig(String[] args) {
		for (int i = 0; args != null && i < args.length; i++) {
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
		outputPath = testPath + "/output";
	}

	public void setSupportDS(TreeSet<nGram> posPickSet2,
			TreeSet<nGram> negPickSet2, TreeSet<nGram> mixPickSet2,
			TreeMap<nGram, Integer> mixPickPosMap2) {
		this.posPickSet = posPickSet2;
		this.negPickSet = negPickSet2;
		this.mixPickSet = mixPickSet2;
		this.mixPickPosMap = mixPickPosMap2;
	}

	public void setTrainingData(ArrayList<Article> posTrainArticles2,
			ArrayList<Article> negTrainArticles2) {
		this.posTrainArticles = posTrainArticles2;
		this.negTrainArticles = negTrainArticles2;
	}

	public void printMixPcik(int K) {
		for (int i = 0, cnt = 0; cnt < K && i < mixPick.size(); i++) {
			nGram e = mixPick.get(i);
			if (e.getNonTerminal() == Ngram) {
				cnt++;
				System.out.printf("Score ( ");
				for (int j = 0; j < e.iWord.length; j++) {
					System.out.printf("%s ",
							ModelUtilities.getWordName(e.iWord[j]));
				}
				System.out.println(")");
			}
		}
	}
}
