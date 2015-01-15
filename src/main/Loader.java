package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import model.Article;
import model.ModelUtilities;
import model.nGram;

public class Loader {
	private File posFolder, negFolder;
	private File testPosFolder, testNegFolder;
	private File wordFile, stopWordsFile, notWordsFile;
	private File posWordsFile, negWordsFile;
	public TreeMap<String, Integer> wordWeight;
	public TreeSet<String> stopWords, notWords, posWords, negWords;
	public ArrayList<Article> posViews, negViews;
	public ArrayList<Article> testPos, testNeg;

	/**
	 * read path + '/pos' & path + '/neg', single file as one views
	 * 
	 * @param path
	 * @param testPath
	 */
	public Loader(String trainingPath, String testPath) {
		posViews = new ArrayList<Article>();
		negViews = new ArrayList<Article>();
		testPos = new ArrayList<Article>();
		testNeg = new ArrayList<Article>();

		posFolder = new File(trainingPath + "/pos");
		negFolder = new File(trainingPath + "/neg");
		testPosFolder = new File(testPath + "/pos");
		testNegFolder = new File(testPath + "/neg");

		listFilesForFolder(posFolder, 1);
		listFilesForFolder(negFolder, -1);
		listFilesForFolder(testPosFolder, 2);
		listFilesForFolder(testNegFolder, -2);

		// = { "the", "are", "is", "it", "he", "she", "-", "a", "an" }
		wordFile = new File(trainingPath + "/extra/AFINN-111.txt");
		wordWeight = new TreeMap<String, Integer>();
		storeWordWeight(wordFile);
		ModelUtilities.addWordWeight(wordWeight);

		stopWordsFile = new File(trainingPath + "/extra/stopwords.txt");
		stopWords = new TreeSet<String>();
		storeWords(stopWordsFile, stopWords);
		ModelUtilities.ignoreToken = stopWords;

		notWordsFile = new File(trainingPath + "/extra/negation_not.txt");
		notWords = new TreeSet<String>();
		storeWords(notWordsFile, notWords);
		ModelUtilities.notToken = notWords;

		posWordsFile = new File(trainingPath + "/extra/pos_word.txt");
		posWords = new TreeSet<String>();
		storeWords(posWordsFile, posWords);
		ModelUtilities.posToken = posWords;
		
		negWordsFile = new File(trainingPath + "/extra/neg_word.txt");
		negWords = new TreeSet<String>();
		storeWords(negWordsFile, negWords);
		ModelUtilities.negToken = negWords;
	}


	public void storeWordWeight(File f) {
		if (f != null && f.getName().charAt(0) == '.')
			return;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(f));
			String line;
			while ((line = fin.readLine()) != null) {
				String[] s = line.split("\\s+");
				if (s.length != 2)
					continue;
				String word = s[0];
				int weight = Integer.parseInt(s[1]);
				if (wordWeight.containsKey(word))
					weight += wordWeight.get(word);
				wordWeight.put(word, weight);
			}
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeWords(File f, TreeSet<String> words) {
		if (f != null && f.getName().charAt(0) == '.')
			return;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(f));
			String line;
			while ((line = fin.readLine()) != null) {
				line = line.trim();
				words.add(line);
			}
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeFile(String path, int kind) {
		File f = new File(path);
		if (f != null && f.getName().charAt(0) == '.')
			return;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(f));
			StringBuilder sb = new StringBuilder();
			String line, content = "";
			while ((line = fin.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			content = sb.toString();
			switch (kind) {
			case 1:
				posViews.add(new Article(content, 1, f.getName()));
				break;
			case -1:
				negViews.add(new Article(content, -1, f.getName()));
				break;
			case 2:
				testPos.add(new Article(content, 1, f.getName()));
				break;
			case -2:
				testNeg.add(new Article(content, -1, f.getName()));
				break;
			}
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listFilesForFolder(final File folder, int kind) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry, kind);
			} else {
				storeFile(fileEntry.getPath(), kind);
			}
		}
	}

	public void writeGram(ArrayList<nGram> p) {
		ArrayList<nGram> mixPick = new ArrayList<nGram>(p);
		try {
			File A;
			PrintWriter printWriter;
			A = new File(Main.workConfig.outputPath + "/feature/ngram.txt");
			A.getParentFile().mkdirs();

			printWriter = new PrintWriter(A);
			for (int i = 0; i < mixPick.size(); i++) {
				nGram e = mixPick.get(i);
				printWriter.printf("Score ( ");
				for (int j = 0; j < e.iWord.length; j++) {
					printWriter.printf("%s ",
							ModelUtilities.getWordName(e.iWord[j]));
				}
				printWriter.println(")");
			}
			printWriter.close();
		} catch (Exception e) {

		}
	}
}
