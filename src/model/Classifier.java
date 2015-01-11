package model;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import main.Article;

public interface Classifier {
	public boolean classify(TreeMap<Integer, Double> x);

	public void training(ArrayList<Article> posTrainArticles,
			ArrayList<Article> negTrainArticles, int kind);

	public void setLimited(TreeSet<Integer> limitSet);
}
