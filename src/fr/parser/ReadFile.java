package fr.parser;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {

	private String path;
	private boolean append_to_file = false;
	
	public ReadFile(String file_path) {
		path = file_path;
	}
	
	public ArrayList<String> readFromFile() throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String> textData = new ArrayList<String>();
		String currentLine = "";
		
		while((currentLine = br.readLine()) != null) {
				textData.add(currentLine);
		}
		
		br.close();
		return textData;
		
	}
	
	
	
	
}
