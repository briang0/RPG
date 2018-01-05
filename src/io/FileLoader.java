package io;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {

	public static ArrayList<String> getTextData(String dir) throws IOException{
		
		Scanner scan = new Scanner(new File(dir));
		ArrayList<String> data = new ArrayList<String>();
		
		while(scan.hasNext()){
			String line = scan.nextLine();
			data.add(line);
		}
		scan.close();
		
		return data;
	}
	
}
