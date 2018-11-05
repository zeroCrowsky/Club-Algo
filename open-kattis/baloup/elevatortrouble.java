import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class elevatortrouble {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		int f = in.nextInt(); // total number of floor
		int s = in.nextInt(); // initial floor
		int g = in.nextInt(); // target floor
		int u = in.nextInt(); // up button number
		int d = in.nextInt(); // down button number
		
		long[] dp = new long[f];
		
		Arrays.fill(dp, -1);
		
		// not optimal solution :
		// complete(dp, s - 1, 0, u, d, g);
		
		
		Set<Integer> idx = new HashSet<>();
		idx.add(s - 1);
		
		long currP = 0;
		
		while(!idx.isEmpty() && dp[g-1] < 0) {

			Set<Integer> newIdx = new HashSet<>();
			
			for (int i : idx) {
				dp[i] = currP;
				int iu = i + u;
				if (iu < dp.length && dp[iu] < 0)
					newIdx.add(iu);
				int id = i - d;
				if (id >= 0 && dp[id] < 0)
					newIdx.add(id);
			}
			
			idx = newIdx;
			
			currP++;
		}
		
		
		
		// System.out.println(Arrays.toString(dp));
		
		long r = dp[g - 1];
		System.out.println(r >= 0 ? r : "use the stairs");
		
		
		
	}
	
	
	/*
	static void complete(long[] dp, int i, long currP, int u, int d, int g) {
		if (i < 0)
			return;
		if (i >= dp.length)
			return;
		
		
		if (dp[i] == -1 || dp[i] >= currP) {
			dp[i] = currP;
			
			complete(dp, i + u, currP + 1, u, d, g);
			complete(dp, i - d, currP + 1, u, d, g);
		}
	}*/

}
