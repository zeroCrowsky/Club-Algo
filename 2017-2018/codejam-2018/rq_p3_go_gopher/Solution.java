package rq_p3_go_gopher;

import java.io.PrintStream;
import java.util.Scanner;

public class Solution {
	
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			
			int t = s.nextInt();
			for (int i = 0; i < t; i++) {
				int a = s.nextInt();
				
				int[] littlest = areaDimension(a);
				
				// System.err.println(Arrays.toString(littlest));
				
				boolean[][] area = new boolean[littlest[0]][littlest[1]];
				int[][] mat = new int[littlest[0]][littlest[1]];
				
				
				int[] bestPos = new int[]{1, 1};
				for(;;) {
					
					for (int r = 1; r < littlest[0] - 1; r++) {
						for (int c = 1; c < littlest[1] - 1; c++) {
							if (mat[r][c] < 9 && mat[r][c] < mat[bestPos[0]][bestPos[1]]) {
								bestPos[0] = r;
								bestPos[1] = c;
							}
						}
					}
					
					// printBoolArray(System.err, area);
					
					System.out.println((bestPos[0] + 1) + " " + (bestPos[1] + 1));
					System.out.flush();
					
					int diggedR = s.nextInt();
					int diggedC = s.nextInt();
					if (diggedR == -1 && diggedC == -1) {
						System.err.println("received -1");
						return;
					}
					
					if (diggedR == 0 && diggedC == 0) {
						break; // YEAY
					}
					
					diggedR--;
					diggedC--;
					
					if (!area[diggedR][diggedC]) {
						area[diggedR][diggedC] = true;
						for (int r = Math.max(diggedR - 1, 0); r <= Math.min(diggedR + 1, littlest[0] - 1); r++) {
							for (int c = Math.max(diggedC - 1, 0); c <= Math.min(diggedC + 1, littlest[1] - 1); c++) {
								mat[r][c]++;
							}
						}
					}
				}
			}
		}
	}
	
	
	
	public static int[] areaDimension(int a) {
		int[] littlest = new int[] {1000, 1000};
		for (int r = Math.min(1000, a); r >= 3; r--) {
			int c = (int) Math.ceil(a / (double)r);
			int currentArea = r * c;
			int littlestArea = littlest[0] * littlest[1];
			if (c >= 3 && currentArea >= a && currentArea < littlestArea) {
				littlest[0] = r;
				littlest[1] = c;
			}
		}
		return littlest;
	}
	
	
	
	
	public static void printBoolArray(PrintStream out, boolean[][] a) {
		out.print("   ");
		for (int c = 0; c < a[0].length; c++)
			out.print(c+1 >= 100 ? (c+1)/100 : " ");
		out.print("\n   ");
		for (int c = 0; c < a[0].length; c++)
			out.print(c+1 >= 10 ? ((c+1)%100)/10 : " ");
		out.print("\n   ");
		for (int c = 0; c < a[0].length; c++)
			out.print((c+1)%10);
		out.println();
		for (int r = 0; r < a.length; r++) {
			if (r+1 < 10)
				out.print("  " + (r+1));
			else if (r+1 < 100)
				out.print(" " + (r+1));
			else
				out.print(r+1);

			for (int c = 0; c < a[r].length; c++)
				out.print(a[r][c] ? "x" : "_");
			out.println();
		}
	}
	
}
