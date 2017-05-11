package me.alexishaldy.util;

import java.util.Scanner;

public class Reader {
	public static Scanner scan = new Scanner(System.in);
	
	public static String readString(String... s) {
		String output="";
		while (true) {
			System.out.print(s.length>0 ? s[0] : ""+"\n-> ");
	        output = scan.nextLine();
	        if (output.isEmpty())
	        	continue;
			return output;		
		}	
	}
	
	public static int readInt(String... s) {
		String output="";
		while (true) {
			System.out.print(s.length>0 ? s[0] : ""+"\n-> ");
			output = scan.nextLine();
	        try {
	        	int i = Integer.parseInt(output);
	        	return i;
	        } catch (Exception e) {
	        	continue;
	        }
		}	
	}
}
