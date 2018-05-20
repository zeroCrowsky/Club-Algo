

import java.util.Scanner;

/*
 * Exercice 1
 */
public class Ex1Bataille {
	public static void main( String[] argv ) throws Exception {
		try (Scanner in = new Scanner(System.in)) {
			
			int n = in.nextInt();
			
			int a = 0, b = 0;			
			for (int i = 0; i < n; i++) {
				int vA = in.nextInt();
				int vB = in.nextInt();
				
				if (vA > vB)
					a++;
				if (vB > vA)
					b++;
				
			}
			
			if (a > b)
				System.out.println("A");
			else
				System.out.println("B");
			
			
		}
	}
}