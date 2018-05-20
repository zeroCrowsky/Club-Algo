

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 * Exercice 4
 */
public class Ex4ADN {
	public static void main( String[] argv ) throws Exception {
		try (Scanner in = new Scanner(System.in)) {
			
			int n = in.nextInt();
			
			String[] chunks = new String[n];
			
			int totSize = 0;
			
			for (int i = 0; i < n; i++) {
				chunks[i] = in.next();
				totSize += chunks[i].length();
			}
			
			int brinSize = totSize / 2;
			
			List<String> poss = getAllPossibility(Arrays.asList(chunks), brinSize);
			
			
			boolean found = false;
			for (int i = 0; i < poss.size() && !found; i++) {
					String s1 = poss.get(i).trim();
				for (int j = i + 1; j < poss.size() && !found; j++) {
					String s2 = poss.get(j).trim();
					
					
					boolean ok = true;
					
					for (int k1 = 0, k2 = 0; k1 < s1.length() && k2 < s2.length();) {
						if (s1.charAt(k1) == ' ') k1++;
						if (s2.charAt(k2) == ' ') k2++;
						if ((s1.charAt(k1) == 'A' && s2.charAt(k2) != 'T')
								|| (s1.charAt(k1) == 'T' && s2.charAt(k2) != 'A')
								|| (s1.charAt(k1) == 'C' && s2.charAt(k2) != 'G')
								|| (s1.charAt(k1) == 'G' && s2.charAt(k2) != 'C')) {
							ok = false;
							break;
						}

						k1++; k2++;
						
						
					}
					
					if (ok) {
						
						System.out.println(s1.trim() + "#" + s2.trim());
						
						found = true;
					}
					
				}
			}
			
			
			
			
			
		}
	}
	
	
	
	
	
	public static List<String> getAllPossibility(List<String> chunks, int size) {
		List<String> l = new ArrayList<>();
		
		if (size == 0) {
			l.add("");
			return l;
		}
		
		for (String c : chunks) {
			if (c.length() > size)
				continue;
			
			List<String> a = new ArrayList<>(chunks);
			a.remove(c);
			
			List<String> sub = getAllPossibility(a, size - c.length());
			
			for (int i = 0; i < sub.size(); i++)
				sub.set(i, c + " " + sub.get(i));
			
			l.addAll(sub);
		}
		
		
		
		
		return l;
	}
	
	
	
	
	
}