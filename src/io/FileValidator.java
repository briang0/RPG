package io;

import java.io.File;

public class FileValidator {
	
	private final static String dirName = System.getProperty("user.home") + "\\testname\\";

	public static boolean validateFiles(){
		boolean output = true;
		
		if (!checkMainDir()){
			output = false;
		}
		if (!checkSaveFolder()){
			output = false;
		}
		if (!checkScreenshotFolder()){
			output = false;
		}
		
		return output;
	}
	
	private static boolean checkMainDir(){
		File f = new File(dirName);
		if (f.exists()){
			return true;
		}else{
			f.mkdir();
		}
		return false;
	}
	
	public static boolean checkSaveFolder(){
		File f = new File(dirName + "saves\\");
		if (f.exists()){
			return true;
		}else{
			f.mkdir();
		}
		return false;
	}
	
	public static boolean checkScreenshotFolder(){
		File f = new File(dirName + "screenshots\\");
		if (f.exists()){
			return true;
		}else{
			f.mkdir();
		}
		return false;
		
	}
}
