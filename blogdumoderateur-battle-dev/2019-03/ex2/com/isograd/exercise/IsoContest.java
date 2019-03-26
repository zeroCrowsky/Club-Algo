package com.isograd.exercise;

import java.util.Scanner;

public class IsoContest {

	/*
	 * Exercice 2
	 */
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			
			int nAR = 1;
			int currW = 0;
			int n = s.nextInt();
			for (int i = 0; i < n; i++) {
				int p = s.nextInt();
				if (currW + p > 100) {
					nAR++;
					currW = p;
				}
				else
					currW += p;
			}
			
			System.out.println(nAR);
		}
	}
	
}
