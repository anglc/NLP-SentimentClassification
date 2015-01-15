package model;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import comp.ReturnCell;

public class Article {
	public String content, fileName;
	public int polarity, predict_polarity;
	public int sentCount, tokenCount;
	public TreeMap<Integer, Double> vec;
	public TreeMap<nGram, Integer> occGramVec;

	public Article(String content, int polarity, String fileName) {
		this.content = content;
		this.polarity = polarity;
		this.fileName = fileName;
	}

	public Article(String content, int polarity) {
		this(content, polarity, "");
	}

	public void preprocess(int Ngram, TreeSet<nGram> posPickSet,
			TreeSet<nGram> negPickSet, ArrayList<nGram> mixPick,
			TreeMap<nGram, Integer> mixPickPosMap) {
		ReturnCell<Integer> sentCount = new ReturnCell<Integer>(0);
		ReturnCell<Integer> tokenCount = new ReturnCell<Integer>(0);

		vec = ModelUtilities.getCharacteristicWeightVector(content, Ngram,
				mixPickPosMap, sentCount, tokenCount);

		occGramVec = ModelUtilities.getNgramOcc(content, Ngram, mixPickPosMap);

		this.sentCount = sentCount.get();
		this.tokenCount = tokenCount.get();
	}
}
