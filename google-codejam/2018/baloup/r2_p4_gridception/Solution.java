package r2_p4_gridception;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// not working
public class Solution {
	static final boolean PARALLEL = true;
	
	static final Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		List<Case> c = IntStream.rangeClosed(1, in.nextInt()).mapToObj(i -> new Case()).collect(Collectors.toList());
		List<String> results = (PARALLEL ? c.parallelStream() : c.stream()).map(Case::run).collect(Collectors.toList());
		for (int i = 0; i < results.size(); i++) System.out.println("Case #" + (i+1) + ": " + results.get(i));
	}
	
	static class Case {
		// input variables
		int R, C;
		boolean[][] pattern;
		
		Case() {
			// read input
			R = in.nextInt();
			C = in.nextInt();
			pattern = new boolean[R][C];
			for (int r = 0; r < R; r++) {
				char[] line = in.next().toCharArray();
				for (int c = 0; c < C; c++)
					pattern[r][c] = line[c] == 'W';
			}
		}
		
		String run() {
			// compute result
			
			int maxPatternSize = 0;
			
			for (int t = 0; t < 16; t++) {

				for (int rDec = 0; rDec <= R; rDec++)
					for (int cDec = 0; cDec <= C; cDec++) {
						boolean[][] matMatch = new boolean[R][C];
						
						for (int r = 0; r < R; r++)
							for (int c = 0; c < C; c++) {
								matMatch[r][c] = pattern[r][c] == patternTest(r+rDec, c+cDec, t);
							}
						
						
						int patSize = maxPatternSize(matMatch);
						
						maxPatternSize = Math.max(maxPatternSize, patSize);
						
						//System.err.println(t + " " + rDec + " " + cDec + " " + patSize + " " + maxPatternSize);
						//System.err.println(boolArray(matMatch));
						
						
					}
				
			}
			
			
			
			
			
			return maxPatternSize + "";
		}
		

		int maxPatternSize(boolean[][] mat) {
			boolean[][] tracked = new boolean[mat.length][mat[0].length];
			int max = 0;
			for (int r = 0; r < mat.length; r++)
				for (int c = 0; c < mat[r].length; c++) {
					if (mat[r][c] && !tracked[r][c]) {
						int count = recursivePattern(mat, tracked, r, c);
						max = Math.max(count, max);
					}
					tracked[r][c] = true;
				}
			return max;
		}
		

		int recursivePattern(boolean[][] mat, boolean[][] tracked, int r, int c) {
			if (r < 0 || r >= R || c < 0 || c >= C || !mat[r][c] || tracked[r][c])
				return 0;
			tracked[r][c] = true;
			return 1
					+ recursivePattern(mat, tracked, r+1, c)
					+ recursivePattern(mat, tracked, r, c+1)
					+ recursivePattern(mat, tracked, r-1, c)
					+ recursivePattern(mat, tracked, r, c-1);
		}
		

		
		
		boolean patternTest(int r, int c, int t) {
			boolean tl = (t & 0b1000) > 0;
			boolean tr = (t & 0b0100) > 0;
			boolean bl = (t & 0b0010) > 0;
			boolean br = (t & 0b0001) > 0;

			if (r < R && c < C)
				return tl;
			if (r < R && c >= C)
				return tr;
			if (r >= R && c < C)
				return bl;
			if (r >= R && c >= C)
				return br;
			
			return false;
		}
	}
	
	
	
	
	
	static String boolArray(boolean[][] mat) {
		String ret = "";
		for (int r = 0; r < mat.length; r++) {
			for (int c = 0; c < mat[r].length; c++) {
				ret += mat[r][c] ? "#" : ".";
			}
			ret += "\n";
		}
		return ret;
	}
	
	
	
}
