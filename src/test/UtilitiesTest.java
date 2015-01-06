package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ModelUtilities;
import model.nGram;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilitiesTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTransformNgram() {
		String stmt = "a, b c, d : abc";
		ArrayList<nGram> t = ModelUtilities.transformNgram(stmt, 2);
		String result = "";
		for (nGram e : t) {
			for (int j = 0; j < e.iWord.length; j++) {
				result += ModelUtilities.getWordName(e.iWord[j]) + " ";
				System.out.printf("%s ", ModelUtilities.getWordName(e.iWord[j]));
			}
			result += "\n";
		}
		assertEquals(result, "a  \nb c \nc  \nd  \nabc  \n");
	}
}
