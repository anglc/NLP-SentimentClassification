package model;

import java.util.TreeMap;

public interface Classifier {
	public boolean classify(TreeMap<Integer, Double> x);
}
