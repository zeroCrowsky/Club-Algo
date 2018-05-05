package r1c_p2_lollipop_shop;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Solution {
	
	public static void main(String[] args) {
		
		try (Scanner s = new Scanner(new BufferedInputStream(System.in))) {
			int T = s.nextInt();
			for (int t = 1; t <= T; t++)
				testCase(s);
		}
	}
	
	
	static void testCase(Scanner in) {
		int N = in.nextInt();

		int[] proba = new int[N];
		boolean[] sold = new boolean[N];
		
		for (int n = 0; n < N; n++) {
			int D = in.nextInt();
			if (D == -1) {
				System.exit(0);
			}
			int[] liked = new int[D];
			for (int d = 0; d < D; d++) {
				liked[d] = in.nextInt();
				proba[liked[d]]++;
			}
			
			int min = -1, minI = -1;
			for (int d = 0; d < D; d++) {
				int lId = liked[d];
				if (sold[lId])
					continue;
				if (min >= 0 && proba[lId] > min)
					continue;
				minI = lId;
				min = proba[lId];
			}
			
			if (minI >= 0)
				sold[minI] = true;
			
			System.out.println(minI);
			System.out.flush();
		}
		
		
	}
	
	
	
}
