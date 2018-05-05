package r1c_p3_ant_stack;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	
	public static void main(String[] args) {
		
		try (Scanner s = new Scanner(new BufferedInputStream(System.in))) {
			int T = s.nextInt();
			for (int t = 1; t <= T; t++)
				System.out.println("Case #" + t + ": "+testCase(s));
		}
	}
	
	
	static String testCase(Scanner in) {
		int N = in.nextInt();
		long[] W = new long[N];
		for (int i = 0; i < N; i++)
			W[i] = in.nextInt();
		
		long[][] g = new long[N][140]; // max 120 Mb (10^5 * 150 * 8)

		Arrays.fill(g[0], Long.MAX_VALUE);
		g[0][0] = 0;
		g[0][1] = W[0];
		
		for (int x = 1; x < N; x++) {
			Arrays.fill(g[x], Long.MAX_VALUE);
			g[x][0] = 0;
			for (int y = 1; y < 140; y++) {
				if (g[x-1][y-1] == Long.MAX_VALUE)
					break;
				long vNotAdded = g[x-1][y]; // if current not added
				long vAdded = g[x-1][y-1]; // if current added
				
				if (vAdded <= 6 * W[x]) // current can be added
					g[x][y] = Math.min(vNotAdded, vAdded + W[x]);
				else
					g[x][y] = vNotAdded;
				
				
			}
		}
		
		for (int i = 139; i >= 0; i--)
			if (g[N-1][i] < Long.MAX_VALUE)
				return "" + i;
		
		
		return "0";
	}
	
}
