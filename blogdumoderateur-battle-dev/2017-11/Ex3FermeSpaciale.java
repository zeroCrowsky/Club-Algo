

import java.util.Scanner;

/*
 * Exercice 3
 */
public class Ex3FermeSpaciale {
	public static void main( String[] argv ) throws Exception {
		try (Scanner in = new Scanner(System.in)) {
			int n = in.nextInt();
			
			char[][] ch = new char[n][n];
			
			for (int i = 0; i < n; i++) {
				String line = in.next();
				ch[i] = line.toCharArray();
			}
			
			int count = 0;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					char curr = ch[i][j];
					if (curr != '.')
						continue;
					
					boolean ok = false;
					if ((i > 0 && j > 0 && ch[i-1][j-1] == 'X')
							|| (i > 0 && ch[i-1][j] == 'X')
							|| (i > 0 && j < n-1 && ch[i-1][j+1] == 'X')
							|| (j > 0 && ch[i][j-1] == 'X')
							|| (j < n-1 && ch[i][j+1] == 'X')
							|| (i < n-1 && j > 0 && ch[i+1][j-1] == 'X')
							|| (i < n-1 && ch[i+1][j] == 'X')
							|| (i < n-1 && j < n-1 && ch[i+1][j+1] == 'X'))
						ok = true;
					
					if (ok) count++;
				}
			
			System.out.println(count);
			
		}
	}
}