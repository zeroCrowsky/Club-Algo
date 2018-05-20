package r1c_p1_a_whole_new_word;

import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
		int L = in.nextInt();
		
		@SuppressWarnings("unchecked")
		Set<Character>[] chars = new Set[L];
		for (int i = 0; i < chars.length; i++)
			chars[i] = new HashSet<>();
		
		Set<String> words = new HashSet<>();
		
		for (int i = 0; i < N; i++) {
			String w = in.next();
			words.add(w);
			char[] chs = w.toCharArray();
			for (int j = 0; j < chs.length; j++) {
				chars[j].add(chs[j]);
			}
		}
		
		
		int[] countPossibility = new int[L];
		int currCount = 1;
		for (int i = L - 1; i >= 0; i--) {
			currCount *= chars[i].size();
			countPossibility[i] = currCount;
		}
		
		
		char[] buff = new char[L];
		
		if (search(words, buff, chars, 0, countPossibility)) {
			return new String(buff);
		}
		return "-";
	}
	
	
	static boolean search(Set<String> notAllowed, char[] buff, Set<Character>[] chars, int currC, int[] countPossibility) {
		if (currC == buff.length) {
			return !notAllowed.contains(new String(buff));
		}
		
		for (char c : chars[currC]) {
			
			Set<String> subSetNotAllowed = new HashSet<>(notAllowed);
			subSetNotAllowed.removeIf(s -> s.charAt(currC) != c);
			
			if (countPossibility[currC] == subSetNotAllowed.size())
				continue;
			
			
			buff[currC] = c;
			if (search(subSetNotAllowed, buff, chars, currC+1, countPossibility))
				return true;
		}
		
		return false;
	}
	
	
}
