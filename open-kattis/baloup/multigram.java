import java.io.BufferedInputStream;
import java.util.Scanner;

public class multigram {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		String s = in.next();
		
		int[] charcount = new int[26];
		
		for (int t = 1; t <= s.length()/2; t++) {
			char c = s.charAt(t-1);
			charcount[c-'a']++;
			
			
			if (s.length() % t != 0)
				continue;
			
			boolean equals = true;
			for (int t2 = t; t2 < s.length(); t2 += t) {
				int[] charcount2 = new int[26];
				for (int i = 0; i < t; i++) {
					char c2 = s.charAt(t2+i);
					charcount2[c2-'a']++;
					if (charcount2[c2-'a'] > charcount[c2-'a']) {
						equals = false;
						break;
					}
				}
				
				if (!equals)
					break;
				
			}
			
			if (equals) {
				System.out.println(s.substring(0, t));
				return;
			}
		}
		
		
		
		System.out.println("-1");
	}
	
	
}
