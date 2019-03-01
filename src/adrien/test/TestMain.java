package adrien.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import fr.parser.ReadFile;
import fr.parser.WriteFile;

public class TestMain {

	public static void main(String[] args) {

		String myFile = "testfolder"+File.separator+"testfile.txt";

		WriteFile datawrite = new WriteFile(myFile, true);
		ArrayList<String> linesToWrite = new ArrayList<String>();
		linesToWrite.add("line1");
		linesToWrite.add("toto");
		linesToWrite.add("line3");
		try {
			datawrite.writeToFile(linesToWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ReadFile dataread = new ReadFile(myFile);
		ArrayList<String> linesRead = new ArrayList<String>();
		try {
			linesRead = dataread.readFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String s : linesRead) {
			System.out.println(s);
		}

		try {
			Files.delete(Paths.get(myFile));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
