package fr.parser;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {

	private String path;
	private boolean append_to_file = false;

	public WriteFile(String file_path) {
		path = file_path;
	}

	public WriteFile(String file_path, boolean append_value) {
		path = file_path;
		append_to_file = append_value;
	}

	public void writeToFile( ArrayList<String> textLines ) throws IOException {
		FileWriter write = new FileWriter( path , append_to_file);
		PrintWriter print_line = new PrintWriter( write );

		for (String textLine : textLines)
			print_line.printf( "%s%n" , textLine);

		print_line.close();
	}


}
