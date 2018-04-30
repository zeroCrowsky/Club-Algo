package p1;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
	
	public static void main(String[] args) {
		
		try (Scanner s = new Scanner(System.in)) {
			int T = s.nextInt();
			for (int t = 0; t < T; t++) {
				int N = s.nextInt();
				int L = s.nextInt();
				
				int[] C = new int[L];
				int sumCount = 0;
				for (int i = 0; i < L; i++) {
					C[i] = s.nextInt();
					sumCount += C[i];
				}
				
				int ret = getMaxRoundedSumPorcent(Arrays.stream(C).mapToObj(i -> (Integer)i).collect(Collectors.toList()), sumCount, N, 0);
				
				System.out.println("Case #" + (t+1) + ": "+ret);
				
				
			}
		}
	}
	
	
	
	
	public static int getMaxRoundedSumPorcent(List<Integer> counts, int sumCount, int remainingCount, double currentPercent) {
		if (remainingCount <= 0) {
			return 0;
		}
		
		if (counts.isEmpty()) {
			int maxFound = 0;
			for (int nbL = remainingCount; nbL > 0; nbL--) {
				int minP = remainingCount / nbL;
				int maxP = (remainingCount % nbL == 0) ? minP : minP + 1;
				int remP = remainingCount;
				int sumPercent = 0;
				for (int i = 0; i < nbL; i++) {
					int remL = nbL - i;
					int nbP = (remL * minP < remP) ? maxP : minP;
					remP -= nbP;
					sumPercent += round(nbP * (100 - currentPercent) / (double) remainingCount);
				}
				if (sumPercent > maxFound)
					maxFound = sumPercent;
			}
			return maxFound;
		}
		
		// System.err.println("begin recur ("+counts+", "+sumCount+", "+remainingCount+", "+currentPercent+")");
		
		int c0 = counts.get(0);
		
		int maxFound = 0;
		
		for (int i = c0; i <= remainingCount - sumCount + c0; i++) {
			// System.err.println("loop i="+i);
			double iPercent = i * (100 - currentPercent) / (double) remainingCount;
			
			int newPercent = round(iPercent);
			
			int recurFound = getMaxRoundedSumPorcent(counts.subList(1, counts.size()), sumCount - c0, remainingCount - i, currentPercent + iPercent);
			
			recurFound += newPercent;
			
			if (recurFound > maxFound)
				maxFound = recurFound;
			
		}

		// System.err.println("end recur: "+maxFound);
		
		return maxFound;
		
	}
	
	
	
	
	
	
	public static int round(double p) {
		return (int) Math.round(p);
	}
	
}
