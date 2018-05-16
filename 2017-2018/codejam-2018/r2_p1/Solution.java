package r2_p1;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.function.LongConsumer;
import java.util.stream.IntStream;

public class Solution {
	static final Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	static void forRange(long b, long e, LongConsumer c) { for(long i = b; i < e; i++) c.accept(i); }
	
	public static void main(String[] args) {
		IntStream.rangeClosed(1, in.nextInt()).forEachOrdered(t -> System.out.println("Case #" + t + ": " + testCase()));
	}
	
	
	static String testCase() {
		
		
		
		
		
		
		return null;
	}
	
	
	
	
}
