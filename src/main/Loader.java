package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class Loader {
	private File posFolder, negFolder, wordFile;
	public TreeMap<String, Integer> wordWeight;
	public ArrayList<String> posViews, negViews;

	/**
	 * read path + '/pos' & path + '/neg', single file as one views
	 * 
	 * @param path
	 */
	public Loader(String path) {
		posFolder = new File(path + "/pos");
		negFolder = new File(path + "/neg");
		posViews = new ArrayList<String>();
		negViews = new ArrayList<String>();
		listFilesForFolder(posFolder, 1);
		listFilesForFolder(negFolder, -1);

		wordFile = new File(path + "/extra/AFINN-111.txt");
		wordWeight = new TreeMap<String, Integer>();
		storeWord(wordFile);
	}

	public void storeWord(File f) {
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

	public void storeFile(String path, int kind) {
		File file = new File(path);
		try {
			BufferedReader fin = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line, content = "";
			while ((line = fin.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			content = sb.toString();
			if (kind > 0)
				posViews.add(content);
			else
				negViews.add(content);
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
}
