package com.isograd.exercise;

import java.util.Scanner;

public class IsoContest {

	static int n, bestScore = 0;
	
	static String mapLeft, mapRight, best = "";
	
	/*
	 * Exercice 5
	 * Ne passe pas le test sur le site du contest. Raison très probable : temps d'exécution trop long
	 */
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			n = s.nextInt();
			mapLeft = "";
			mapRight = "";
			char[] mapIn = s.next().toCharArray();
			
			{
				int i = 0;
				while (i < n && mapIn[i] != 'X') {
					mapLeft = mapIn[i] + mapLeft;
					i++;
				}
				i++;
				while (i < n) {
					mapRight += mapIn[i];
					i++;
				}
			}
			
			recur(mapLeft, mapRight, "");
			
			System.out.println(best);
		}
	}
	
	static void recur(String inLeft, String inRight, String buff) {
		if (inLeft.isEmpty() && inRight.isEmpty())
			test(buff);
		else {
			if (!inLeft.isEmpty() && inLeft.charAt(0) == 'o') {
				recur(inLeft.substring(1), inRight, buff + inLeft.substring(0, 1));
			}
			else if (!inRight.isEmpty() && inRight.charAt(0) == 'o') {
				recur(inLeft, inRight.substring(1), buff + inRight.substring(0, 1));
			}
			else {
				if (!inLeft.isEmpty())
					recur(inLeft.substring(1), inRight, buff + inLeft.substring(0, 1));
				if (!inRight.isEmpty())
					recur(inLeft, inRight.substring(1), buff + inRight.substring(0, 1));
			}
			
		}
	}
	
	
	static void test(String s) {
		int score = 0;
		for (char c : s.toCharArray()) {
			if (c == 'o')
				score++;
			else if (c == '*')
				score *= 2;
		}
		
		if (score > bestScore) {
			bestScore = score;
			best = s;
		}
		
	}
	
	
}
