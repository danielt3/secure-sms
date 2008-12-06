/**
 *
 * Copyright (C) 2008 Rodrigo Rodrigues da Silva, Eduardo de Souza Cruz and 
 *                    Geovandro Carlos C. F. Pereira
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

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
