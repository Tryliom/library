package me.alexishaldy.util;

import java.util.Scanner;

public class Reader {
	public static Scanner scan = new Scanner(System.in);
	
	public static String readString(String... s) {
		String output="";
		while (true) {
			System.out.print((s.length>0 ? s[0] : "")+"\n-> ");
	        output = scan.nextLine();
	        if (output.isEmpty())
	        	continue;
			return output;		
		}	
	}
	
	public static String readEmail(String... s) {
		String output="";
		while (true) {
			System.out.print((s.length>0 ? s[0] : "")+"\n-> ");
	        output = scan.nextLine();
	        if (output.isEmpty() || !output.contains("@"))
	        	continue;
			return output;		
		}	
	}
	
	public static String readNumTel(String... s) {
		String output="";
		while (true) {
			System.out.print((s.length>0 ? s[0] : "")+"\n-> ");
	        output = scan.nextLine();
	        String match = "^(0|\\+41|0041)[0-9]{9}$";
	        if (output.isEmpty() || !output.replaceAll(" ", "").matches(match))
	        	continue;
			return output;		
		}	
	}
	
	public static int readInt(String... s) {
		String output="";
		while (true) {
			System.out.print((s.length>0 ? s[0] : "")+"\n-> ");
			output = scan.nextLine();
	        try {
	        	int i = Integer.parseInt(output);
	        	return i;
	        } catch (Exception e) {
	        	continue;
	        }
		}	
	}
	
	public static String readBoolean(String possibleResp, String... s) {
		String output="";
		String resp[] = possibleResp.split(" ");
		while (true) {
			System.out.print((s.length>0 ? s[0] : "")+"\n-> ");
	        output = scan.nextLine();
	        if (output.isEmpty() || !Utils.containString(resp, output))
	        	continue;
			return output;		
		}	
	}
}











