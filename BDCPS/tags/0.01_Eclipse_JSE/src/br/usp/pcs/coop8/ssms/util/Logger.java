package br.usp.pcs.coop8.ssms.util;

public class Logger {

	public static final int NONE = 0;
	public static final int INFO = 1;
	public static final int WARN = 2;
	public static final int DEBUG = 3;
		
	private int mode;
	
	public Logger(int mode){
		this.mode = mode;
	}
	
	private void log(String message, int mode){
		if (mode <= this.mode){
			System.out.println("["+ mode(mode) +"]" + " " + message);
		}
	}
	
	public void debug(String message) {
		log (message, DEBUG);
	}
	
	public void warn(String message) {
		log (message, WARN);
	}
	
	public void info(String message) {
		log (message, INFO);
	}

	private String mode(int mode) {
		String ret = "NULL";
		switch (mode){
		case NONE: 
			ret = "NONE";
			break;
		case INFO:
			ret = "INFO";
			break;
		case WARN: 
			ret = "WARN";
			break;
		case DEBUG: 
			ret = "DEBUG";
			break;		
		}		
		return ret;
	}
}
