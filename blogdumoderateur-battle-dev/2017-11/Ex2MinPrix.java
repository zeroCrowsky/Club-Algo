

import java.util.Scanner;

/*
 * Exercice 2
 */
public class Ex2MinPrix {
	public static void main( String[] argv ) throws Exception {
		try (Scanner in = new Scanner(System.in)) {
			
			int n = in.nextInt();
			
			String name = in.next();
			
			int tmin = Integer.MAX_VALUE;
			
			for (int i = 0; i < n; i++) {
				String na = in.next();
				int p = in.nextInt();
				if (!na.equalsIgnoreCase(name))
					continue;
				
				if (p < tmin)
					tmin = p;
				
			}
			
			
			System.out.println(tmin);
			
		}
	}
}