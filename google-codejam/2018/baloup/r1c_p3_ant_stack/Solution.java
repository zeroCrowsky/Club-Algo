package r1c_p3_ant_stack;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		int N;
		long[] W;
		Case() {
			// read input
			N = in.nextInt();
			W = new long[N];
			for (int i = 0; i < N; i++)
				W[i] = in.nextInt();
		}
		
		String run() {
			long[][] g = new long[2][140];

			Arrays.fill(g[1], Long.MAX_VALUE);
			g[1][0] = 0;
			g[1][1] = W[0];
			
			for (int x = 1; x < N; x++) {
				g[0] = g[1];
				g[1] = new long[140];
				Arrays.fill(g[1], Long.MAX_VALUE);
				g[1][0] = 0;
				for (int y = 1; y < 140; y++) {
					if (g[0][y-1] == Long.MAX_VALUE)
						break;
					long vNotAdded = g[0][y]; // if current not added
					long vAdded = g[0][y-1]; // if current added
					
					if (vAdded <= 6 * W[x]) // current can be added
						g[1][y] = Math.min(vNotAdded, vAdded + W[x]);
					else
						g[1][y] = vNotAdded;
					
					
				}
			}
			
			for (int i = 139; i >= 0; i--)
				if (g[1][i] < Long.MAX_VALUE)
					return "" + i;
			
			
			return "0";
		}
	}
	
	
}
