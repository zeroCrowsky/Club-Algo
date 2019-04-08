package com.isograd.exercise;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class IsoContest_NonExaust {

	static int n;
	
	static Stack<Character> mapLeft, mapRight;
	
	/*
	 * Exercice 5
	 */
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			n = s.nextInt();
			mapLeft = new Stack<>();
			mapRight = new Stack<>();
			char[] mapIn = s.next().toCharArray();
			
			{
				int i = 0;
				while (i < n && mapIn[i] != 'X') {
					mapLeft.push(Character.valueOf(mapIn[i]));
					i++;
				}
				i++;
				while (i < n) {
					mapRight.add(0, Character.valueOf(mapIn[i]));
					i++;
				}
			}
			
			String ret = "";
			System.err.println();
			System.err.println(Arrays.toString(mapIn));
			System.err.println(mapLeft);
			System.err.println(mapRight);
			
			
			
			
			
			System.out.println(ret);
		}
	}
	
	
	static int[] nbStarBeforeGoldAndNbGoldAfterStars(Stack<Character> s) {
		int[] n = new int[2];
		int i;
		for (i = s.size() - 1; i >= 0; i--) {
			if (s.get(i).charValue() == 'o')
				break;
			n[0]++;
		}
		for (; i >= 0; i--) {
			if (s.get(i).charValue() != 'o')
				break;
			n[1]++;
		}
		return n; // pas de pièce du tout
	}
	
}
