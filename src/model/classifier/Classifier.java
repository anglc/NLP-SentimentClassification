package model.classifier;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import model.Article;

public interface Classifier {
	public static final int DEFAULT_ITLIMIT = 32;

	public boolean classify(TreeMap<Integer, Double> x);

	public void training(ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, int kind);

	public void setLimited(TreeSet<Integer> limitSet);
}
