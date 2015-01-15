package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Article;

public class ShuffleChooser {
	public List<Article> posViews, negViews;
	public List<Article> posTrain, negTrain, posTest, negTest;

	public ShuffleChooser(List<Article> posViews) {
		this.posViews = new ArrayList<Article>(posViews);
		this.negViews = new ArrayList<Article>();
		shuffle(1, 1);
	}

	public ShuffleChooser(List<Article> posViews, List<Article> negViews) {
		this.posViews = new ArrayList<Article>(posViews);
		this.negViews = new ArrayList<Article>(negViews);
		shuffle(1, 1);
	}

	/**
	 * example 1. shuffle(1, 1), if 1000 items, training 500, testing 500
	 * example 2. shuffle(4, 1), if 1000 items, training 800, testing 200
	 * 
	 * @param trainingRatio
	 * @param testingRatio
	 */
	public void shuffle(int trainingRatio, int testingRatio) {
		Collections.shuffle(posViews);
		Collections.shuffle(negViews);
		int divPos = this.posViews.size() * trainingRatio
				/ (trainingRatio + testingRatio);
		int divNeg = this.negViews.size() * trainingRatio
				/ (trainingRatio + testingRatio);
		posTrain = posViews.subList(0, divPos);
		posTest = posViews.subList(divPos, posViews.size());
		negTrain = negViews.subList(0, divNeg);
		negTest = negViews.subList(divNeg, negViews.size());
	}
}
