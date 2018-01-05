package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileWriter {

	public static void writeErrorLog(String filePath, ArrayList<String> data){
		File location = new File(filePath);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(location);
		} catch (FileNotFoundException e) {
			data.add(e.getMessage());
			e.printStackTrace();
		}
		
		for (int x = 0; x < data.size(); x++){
			writer.write(data.get(x)+"/n");
		}
		
	}
	
}
