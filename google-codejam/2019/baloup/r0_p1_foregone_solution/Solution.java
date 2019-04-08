package r0_p1_foregone_solution;

import java.io.BufferedInputStream;
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
		
		String N;
		
		Case() {
			// read input
			N = in.next();
		}
		
		String run() {
			
			String N1 = "";
			String N2 = "";
			
			for (char c : N.toCharArray()) {
				if (c == '4') {
					N1 += '3';
					N2 += '1';
				}
				else {
					N1 += c;
					if (!N2.isEmpty())
						N2 += '0';
				}
			}
			
			return N1 + " " + N2;
		}
	}
	
	
	
	
	
}
