package r1a_p1_waffle_choppers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			int t = s.nextInt();
			for (int i = 0; i < t; i++) {
				int R = s.nextInt();
				int C = s.nextInt();
				int h = s.nextInt();
				int v = s.nextInt();
				
				boolean[][] mat = new boolean[R][C];
				int[] rows = new int[R];
				int[] cols = new int[C];
				int total = 0;
				Map<Integer, Integer> rowsDistribution = new HashMap<>();
				Map<Integer, Integer> colsDistribution = new HashMap<>();
				int[][] chocoDistribution = new int[h+1][v+1];
				
				for (int r = 0; r < R; r++) {
					char[] line = s.next().toCharArray();
					for (int c = 0; c < C; c++) {
						boolean choco = line[c] == '@';
						mat[r][c] = choco;
						if (choco) {
							rows[r]++;
							cols[c]++;
							total++;
						}
					}
				}
				
				boolean impossible = false;
				
				if (total % (h + 1) != 0 || total % (v + 1) != 0) {
					System.out.println("Case #" + (i + 1) + ": IMPOSSIBLE");
					continue;
				}
				
				// test rows
				
				int chocoPerCuttedRow = total / (h + 1);
				
				int currSum = 0;
				int currRow = 0;
				for (int r = 0; r < R; r++) {
					currSum += rows[r];
					rowsDistribution.put(r, currRow);
					if (currSum > chocoPerCuttedRow) {
						impossible = true;
						break;
					}
					if (currSum == chocoPerCuttedRow) {
						currSum = 0;
						currRow++;
					}
				}
				if (currSum != 0)
					impossible = true;
				
				if (impossible) {
					System.out.println("Case #" + (i + 1) + ": IMPOSSIBLE");
					continue;
				}
				
				// test cols
				
				int chocoPerCuttedCols = total / (v + 1);
				
				currSum = 0;
				int currCol = 0;
				for (int c = 0; c < C; c++) {
					currSum += cols[c];
					colsDistribution.put(c, currCol);
					if (currSum > chocoPerCuttedCols) {
						impossible = true;
						break;
					}
					if (currSum == chocoPerCuttedCols) {
						currSum = 0;
						currCol++;
					}
				}
				if (currSum != 0)
					impossible = true;
				
				if (impossible) {
					System.out.println("Case #" + (i + 1) + ": IMPOSSIBLE");
					continue;
				}

				for (int r = 0; r < R; r++) {
					for (int c = 0; c < C; c++) {
						if (mat[r][c]) {
							chocoDistribution[rowsDistribution.get(r)][colsDistribution.get(c)]++;
						}
					}
				}
				
				int value = chocoDistribution[0][0];
				for (int r = 0; r < h + 1 && !impossible; r++) {
					for (int c = 0; c < v + 1; c++) {
						if (chocoDistribution[r][c] != value) {
							impossible = true;
							break;
						}
					}
				}
				
				if (impossible) {
					System.out.println("Case #" + (i + 1) + ": IMPOSSIBLE");
				}
				else {
					System.out.println("Case #" + (i + 1) + ": POSSIBLE");
				}
				
				
				
			}
		}
	}
	
}
