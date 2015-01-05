package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShuffleChooser {
	private Loader loader;
	public ArrayList<String> posViews, negViews;
	public List<String> posTrain, negTrain, posTest, negTest;

	public ShuffleChooser(Loader loader) {
		setLoader(loader);
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
		this.posViews = loader.posViews;
		this.negViews = loader.negViews;
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
