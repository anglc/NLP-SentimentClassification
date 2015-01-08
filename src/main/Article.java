package main;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import model.ModelUtilities;
import model.nGram;

public class Article {
	public String content;
	public int polarity;
	public int sentCount, tokenCount;
	public TreeMap<Integer, Double> vec, occVec;
	public TreeMap<nGram, Integer> occGramVec;

	public Article(String content, int polarity) {
		this.content = content;
		this.polarity = polarity;
	}

	public void preprocess(int Ngram, TreeSet<nGram> posPickSet,
			TreeSet<nGram> negPickSet, ArrayList<nGram> mixPick,
			TreeMap<nGram, Integer> mixPickPosMap) {
		ReturnCell<Integer> sentCount = new ReturnCell<Integer>(0);
		ReturnCell<Integer> tokenCount = new ReturnCell<Integer>(0);

		vec = ModelUtilities.getCharacteristicWeightVector(content, Ngram,
				mixPickPosMap, sentCount, tokenCount);
		occGramVec = ModelUtilities.getNgramOcc(content, Ngram, mixPickPosMap);
		occVec = new TreeMap<Integer, Double>();
		
		this.sentCount = sentCount.get();
		this.tokenCount = tokenCount.get();
		
		double[] voteArr = new double[5];
		for (Entry<Integer, Double> e : vec.entrySet()) {
			int x = e.getKey();
			if (posPickSet.contains(mixPick.get(x)))
				voteArr[0]++;
			if (negPickSet.contains(mixPick.get(x)))
				voteArr[1]++;
		}
		voteArr[2] = voteArr[0] * voteArr[1];
		voteArr[3] = this.sentCount;
		voteArr[4] = this.tokenCount;
		for (int j = 0; j < 5; j++)
			occVec.put(j, voteArr[j]);
	}
}
