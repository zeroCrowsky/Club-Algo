package com.isograd.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IsoContest {

	static List<String> words = new ArrayList<>();
	
	static String biggest = "";
	/*
	 * Exercice 4
	 */
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			
			int n = s.nextInt();
			for (int i = 0; i < n; i++) {
				words.add(s.next());
			}
			
			String w0 = words.remove(0);
			
			recur(w0, "");
			
			System.out.println(biggest.isEmpty() ? "KO" : biggest);
			
		}
	}
	
	
	static void recur(String w0, String w1) {
		if (w0.isEmpty())
			compute(w1);
		else {
			String w0s = w0.substring(1);
			recur(w0s, w1 + w0.substring(0, 1));
			recur(w0s, w1);
		}
	}
	
	static void compute(String w1) {
		boolean contains = true;
		for (String w : words) {
			if (!containsLetters(w1, w)) {
				contains = false;
				break;
			}
		}
		if (contains && w1.length() > biggest.length()) {
			biggest = w1;
		}
	}
	
	
	
	static boolean containsLetters(String search, String str) {
		int j = 0;
		for (int i = 0; i < str.length() && j < search.length(); i++)
			if (str.charAt(i) == search.charAt(j))
				j++;
		
		return j >= search.length();
	}
	
	
}
