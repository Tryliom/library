package me.alexishaldy.util;

public class Reader {
	
	public static String readString(String... s) {
		String output="";
		while (true) {
			System.out.print(s.length>0 ? s[0] : ""+"\n-> ");
	        output = System.console().readLine();
			return output;		
		}	
	}
	
	public static int readInt(String... s) {
		String output="";
		while (true) {
			System.out.print(s.length>0 ? s[0] : ""+"\n-> ");
	        output = System.console().readLine();
	        try {
	        	int i = Integer.parseInt(output);
	        	return i;
	        } catch (Exception e) {
	        	continue;
	        }
		}	
	}
}
