package r1b_p1_rounding_error;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
	
	public static void main(String[] args) {
		
		try (Scanner s = new Scanner(System.in)) {
			int T = s.nextInt();
			for (int t = 0; t < T; t++) {
				int N = s.nextInt();
				int L = s.nextInt();
				
				List<Lang> langs = new ArrayList<>();
				int currentCount = 0;
				for (int i = 0; i < L; i++) {
					Lang l = new Lang(s.nextInt());
					langs.add(l);
					currentCount += l.C;
				}
				
				for (int i = 0; i < N - currentCount; i++)
					langs.add(new Lang(0));
				
				int[] nbRoundedUp = IntStream.range(1, N).filter(i -> {
					double p = i * 100 / (double) N;
					return (Math.round(p) > p);
				}).toArray();
				
				for (Lang l : langs) {
					for (Integer i : nbRoundedUp) {
						if (i >= l.C) {
							l.targetC = i;
							break;
						}
					}
				}
				
				langs.sort((l1, l2) -> Integer.compare(l1.getDistToTargetCount(), l2.getDistToTargetCount()));
				
				long currentPorcent = 0;
				
				for (Lang l : langs) {
					int add = Math.min(l.getDistToTargetCount(), N - currentCount);
					currentCount += add;
					l.C += add;
					currentPorcent += Math.round(l.C * 100 / (double) N);
				}
				
				
				System.out.println("Case #" + (t+1) + ": "+currentPorcent);
				
				
				
				
			}
		}
	}
	
	
	
	
	static class Lang {
		int C;
		int targetC = Integer.MAX_VALUE;
		
		public Lang(int c) {
			C = c;
		}
		
		int getDistToTargetCount() {
			return targetC - C;
		}
	}
	
	
}
