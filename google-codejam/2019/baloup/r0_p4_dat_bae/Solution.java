package r0_p4_dat_bae;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
	
	static final Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		IntStream.rangeClosed(1, in.nextInt()).forEach(i -> testCase());
	}

	static void testCase() {
		
		int N = in.nextInt();
		int B = in.nextInt();
		int F = in.nextInt();
		
		int[] respSigs = new int[N - B];
		boolean[] resp = new boolean[N - B];
		int[] querySigs = new int[N];
		boolean[] query = new boolean[N];
		
		for (int f = 0; f < F; f++) {
			
			
			// construct input
			int intv = (int)Math.pow(2, f);
			int intvProgress = 0;
			boolean currV = false;
			for (int i = 0; i < N; i++) {
				query[i] = currV;
				intvProgress++;
				if (intvProgress >= intv) {
					intvProgress = 0;
					currV = !currV;
				}
			}
			
			applyDataToSignature(query, querySigs);
			
			System.out.println(boolArrayToString(query));
			System.out.flush();
			
			String respStr = in.next();
			if (respStr.equals("-1")) {
				throw new RuntimeException("Bad answer");
			}
			
			stringToBoolArray(respStr, resp);
			
			applyDataToSignature(resp, respSigs);
		}
		
		boolean[] works = new boolean[N];
		for (int i = 0, m = 0; i < respSigs.length; i++) {
			int sig = respSigs[i];
			for (; m < querySigs.length; m++) {
				if (sig == querySigs[m]) {
					works[m] = true;
					m++;
					break;
				}
			}
		}
		
		List<Integer> brokens = new ArrayList<>();
		for (int i = 0; i < works.length; i++)
			if (!works[i])
				brokens.add(i);
		
		System.out.println(String.join(" ", brokens.stream().map(i -> i.toString()).collect(Collectors.toList())));
		System.out.flush();
		
		if (in.next().equals("-1")) {
			throw new RuntimeException("Bad answer");
		}
	}
	
	
	
	static void applyDataToSignature(boolean[] data, int[] signatures) {
		for (int i = 0; i < signatures.length; i++) {
			signatures[i] <<= 1;
			if (i < data.length && data[i])
				signatures[i] |= 1;
		}
	}
	
	static String boolArrayToString(boolean[] data) {
		char[] ret = new char[data.length];
		for (int i = 0; i < data.length; i++)
			ret[i] = data[i] ? '1' : '0';
		return new String(ret);
	}
	
	static void stringToBoolArray(String input, boolean[] output) {
		for (int i = 0; i < input.length() && i < output.length; i++) {
			output[i] = input.charAt(i) == '1';
		}
	}
	
}
