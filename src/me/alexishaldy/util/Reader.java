package me.alexishaldy.util;

import java.util.Scanner;

public class Reader {
	public static Scanner scan = new Scanner(System.in);
	
	/**
	 * 
	 * @param s Question
	 * @return The response of the question in a String
	 */
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
	
	/**
	 * 
	 * @param s Question
	 * @return Donne la réponse en String qui a été vérifiée comme quoi c'est un email
	 */
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
	
	/**
	 * 
	 * @param s Question
	 * @return Donne la réponse en String en vérifiant que c'est un numéro de téléphone Suisse valide
	 */
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
	
	/**
	 * 
	 * @param s Question
	 * @return Donne la réponse en Integer en vérifiant que c'est un int
	 */
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
	
	/**
	 * 
	 * @param possibleResp Réponses possible que l'utilisateur peut entrer, doivent être séparés d'espaces
	 * @param s Question
	 * @return Donne la réponse en String sur une des réponses possibles
	 */
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











