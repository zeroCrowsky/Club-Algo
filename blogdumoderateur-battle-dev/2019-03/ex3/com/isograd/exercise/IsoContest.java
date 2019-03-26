package com.isograd.exercise;

import java.util.Scanner;

public class IsoContest {
	
	
	static int n, currX = 0, currY = 0, nextX, nextY;
	static char[][] map;
	/*
	 * Exercice 3
	 */
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			
			
			String out = "";
			
			n = s.nextInt();
			map = new char[n][];
			for (int i = 0; i < n; i++) {
				map[i] = s.next().toCharArray();
			}
			
			while (findNext('o')) {
				out += gotoNext() + "x";
				map[currY][currX] = '.';
			}
			
			while (findNext('*')) {
				out += gotoNext() + "x";
				map[currY][currX] = '.';
			}
			
			System.out.println(out);
		}
	}
	
	static boolean findNext(char ch) {
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (map[r][c] == ch) {
					nextX = c;
					nextY = r;
					return true;
				}
			}
		}
		return false;
	}
	
	
	static String gotoNext() {
		String ret = "";
		while (currX < nextX) {
			ret += '>';
			currX++;
		}
		while (currX > nextX) {
			ret += '<';
			currX--;
		}
		while (currY < nextY) {
			ret += 'v';
			currY++;
		}
		while (currY > nextY) {
			ret += '^';
			currY--;
		}
		return ret;
	}
	
	
	
}
