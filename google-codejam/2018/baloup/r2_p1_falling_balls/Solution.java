package r2_p1_falling_balls;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
	static final boolean PARALLEL = false;
	
	static final Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		List<Case> c = IntStream.rangeClosed(1, in.nextInt()).mapToObj(i -> new Case()).collect(Collectors.toList());
		List<String> results = (PARALLEL ? c.parallelStream() : c.stream()).map(Case::run).collect(Collectors.toList());
		for (int i = 0; i < results.size(); i++) System.out.println("Case #" + (i+1) + ": " + results.get(i));
	}
	
	static class Case {
		// input variables
		int C;
		int[] B;
		
		Case() {
			// read input
			C = in.nextInt();
			B = new int[C];
			for (int i = 0; i < C; i++)
				B[i] = in.nextInt();
		}
		
		String run() {
			// compute result
			if (B[0] == 0 || B[C-1] == 0)
				return "IMPOSSIBLE";
			
			int[] remainings  = Arrays.copyOf(B, C);
			int[] shift = new int[C];
			
			int currO = 0;
			for (int currI = 0; currI < C; currI++) {
				for (; currO < C; currO++) if (remainings[currO] != 0) break;
				shift[currI] = currO - currI;
				remainings[currO]--;
			}
			
			int R = Arrays.stream(shift).map(Math::abs).max().orElse(0);
			
			String[] rows = new String[R + 1];
			
			char[] line = new char[C];
			
			for (int r = 0; r < R; r++) {
				int[] shiftNext = new int[C];
				// Arrays.fill(line, '.');
				for (int c = 0; c < C; c++) {
					if (shift[c] > 0) {
						line[c] = '\\';
						// error check
						//if (shiftNext[c+1] != 0 && shiftNext[c+1] != shift[c] - 1)
						//	System.err.println("shiftNextError r="+r+" c="+c);
						shiftNext[c+1] = shift[c] - 1;
					}
					else if (shift[c] < 0) {
						line[c] = '/';
						// error check
						//if (shiftNext[c+1] != 0 && shiftNext[c-1] != shift[c] + 1)
						//	System.err.println("shiftNextError r="+r+" c="+c);
						shiftNext[c-1] = shift[c] + 1;
					}
					else {
						line[c] = '.';
						// error check
						//if (shiftNext[c] != 0 && shiftNext[c] != shift[c])
						//	System.err.println("shiftNextError r="+r+" c="+c);
						shiftNext[c] = shift[c];
					}
				}
				
				rows[r] = new String(line);
				//System.err.println(Arrays.toString(shift));
				shift = shiftNext;
			}
			

			//System.err.println(Arrays.toString(shift));
			
			Arrays.fill(line, '.');
			rows[R] = new String(line);
			
			return (R + 1) + "\n" + String.join("\n", rows);
		}
	}
	
	
	
	
	
}
