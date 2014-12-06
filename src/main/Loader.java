package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
public class Loader {
	private File posFolder, negFolder;
	public ArrayList<String> posViews, negViews;
	/**
	 * read path + '/pos' & path + '/neg', single file as one views
	 * @param path
	 */
	public Loader(String path) {
		posFolder = new File(path + "/pos");
		negFolder = new File(path + "/neg");
		posViews = new ArrayList<String>();
		negViews = new ArrayList<String>();
		listFilesForFolder(posFolder, 1);
		listFilesForFolder(negFolder, -1);
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
