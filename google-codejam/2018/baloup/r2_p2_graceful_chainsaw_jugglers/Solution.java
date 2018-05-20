package r2_p2_graceful_chainsaw_jugglers;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//not working
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
		int R, B;
		Case() {
			// read input
			R = in.nextInt();
			B = in.nextInt();
		}
		
		String run() {
			// compute result
			
			int remR = R, remB = B;
			int count = 0;
			
			//boolean[][] selected = new boolean[R+1][B+1];
			for (int i = 1; i <= remR + remB; i++) {
				System.err.println(i + " " + remR + " " + remB + " " + count);
				for (int r = 0, b = i; r <= R && b >= 0; r++, b--) {
					
					//System.err.println("  " + r + " " + b + " " + remR + " " + remB + " " + count);
					if (b > B || b > remB || r > remR)
						continue;
					//selected[r][b] = true;
					remB -= b;
					remR -= r;
					count++;
				}
			}
			
			//System.err.println(boolArray(selected));
			
			
			
			
			
			return count + "";
		}
	}
	
	/*
	static String boolArray(boolean[][] mat) {
		String ret = "";
		for (int r = 0; r < mat.length; r++) {
			for (int c = 0; c < mat[r].length; c++) {
				ret += mat[r][c] ? "#" : ".";
			}
			ret += "\n";
		}
		return ret;
	}*/
	
	
}
