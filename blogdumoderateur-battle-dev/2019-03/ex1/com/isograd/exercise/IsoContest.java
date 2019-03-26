package com.isograd.exercise;

import java.util.Scanner;

public class IsoContest {
	
	
	/*
	 * Exercice 1
	 */
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			
			int r = s.nextInt();
			for (int i = 0; i < 42; i++) {
				r += s.nextInt() - s.nextInt();
			}
			
			System.out.println(r <= 100 ? "1000" : r <= 10000 ? "100" : "KO");
		}
	}
	
}
