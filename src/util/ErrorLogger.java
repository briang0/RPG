package util;

import java.util.ArrayList;

public class ErrorLogger {

	private ArrayList<String> errorLog;
	
	public ErrorLogger(){
		errorLog = new ArrayList<String>();
	}

	public void addError(String error){
		errorLog.add("Error: " + error);
	}
	
	public void addException(String error){
		errorLog.add("Exception: " + error);
	}
	
	public void dumpErrorLog(boolean reset){
		if (reset){
			
		}else{
			
		}
	}
	
	public void clearLog(){
		errorLog.clear();
	}
	
	public ArrayList<String> getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(ArrayList<String> errorLog) {
		this.errorLog = errorLog;
	}
	
	
	
}
