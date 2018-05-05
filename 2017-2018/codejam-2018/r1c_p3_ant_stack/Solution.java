package r1c_p3_ant_stack;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
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
		
		List<ListLong> stacks = new ArrayList<>();
		int longestOfStacks = 0;
		
		stacks.add(new ListLong());
		
		for (int i = 0; i < N; i++) {
			int I = i;
			long currW = W[i];
			long maxSupportedW = 6 * currW;
			
			List<ListLong> tempL = new ArrayList<>();
			for (ListLong lW : stacks) {
				long sum = lW.sum;
				if (sum > maxSupportedW)
					continue;
				
				ListLong newL = new ListLong(lW);
				newL.add(currW);
				tempL.add(newL);
				longestOfStacks = Math.max(longestOfStacks, newL.count);
			}
			
			stacks.addAll(tempL);
			int los = longestOfStacks;
			stacks.removeIf(l -> l.count < los - (N - I));
			
			stacks.sort((l1, l2) -> Long.compare(l1.sum, l2.sum));
			for (int j = 1; j < stacks.size();) {
				ListLong l1 = stacks.get(j-1);
				ListLong l2 = stacks.get(j);
				if (l1.sum != l2.sum) {
					j++;
					continue;
				}
				if (l1.count >= l2.count)
					stacks.remove(j);
				else
					stacks.remove(j-1);
			}
		}
		
		
		return "" + stacks.stream().mapToInt(l -> l.count).max().orElse(0);
	}
	
	
	
	static class ListLong {
		long sum;
		int count;
		
		public ListLong() {
			sum = 0;
			count = 0;
		}
		
		public ListLong(ListLong o) {
			sum = o.sum;
			count = o.count;
		}
		
		public void add(long v) {
			sum += v;
			count++;
		}
		
	}
	
	
	
}
