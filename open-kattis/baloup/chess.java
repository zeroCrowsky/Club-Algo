import java.io.BufferedInputStream;
import java.util.Scanner;

public class chess {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int T = in.nextInt();
		
		
		for (int t = 0; t < T; t++) {
			char c1 = in.next().charAt(0);
			int r1 = in.nextInt();
			char c2 = in.next().charAt(0);
			int r2 = in.nextInt();
			if (c1 == c2 && r1 == r2)
				System.out.println("0 " + c1 + " " + r1);
			else {
				int d1Id1 = diag1Id(r1, c1);
				int d1Id2 = diag1Id(r2, c2);
				int d2Id1 = diag2Id(r1, c1);
				int d2Id2 = diag2Id(r2, c2);
				
				if ((d1Id1 % 2) != (d1Id2 % 2))
					System.out.println("Impossible");
				else if (d1Id1 == d1Id2 || d2Id1 == d2Id2) {
					System.out.println("1 " + c1 + " " + r1 + " " + c2 + " " + r2);
				}
				else {
					int rI1 = rId(d1Id1, d2Id2);
					char cI1 = cId(d1Id1, d2Id2);
					int rI2 = rId(d1Id2, d2Id1);
					char cI2 = cId(d1Id2, d2Id1);
					
					if (rI1 > 0 && rI1 <= 8 && cI1 >= 'A' && cI1 <= 'H') {
						System.out.println("2 " + c1 + " " + r1 + " " + cI1 + " " + rI1 + " " + c2 + " " + r2);
					}
					else {
						System.out.println("2 " + c1 + " " + r1 + " " + cI2 + " " + rI2 + " " + c2 + " " + r2);
					}
				}
			}
			
		}
	}
	
	
	

	public static int colToIndex(char c) {
		return Character.toUpperCase(c) - 'A' + 1;
	}
	
	public static char indexToCol(int c) {
		return (char) ('A' - 1 + c);
	}
	
	
	public static int diag1Id(int r, char c) {
		return r + colToIndex(c) - 1;
	}
	
	public static int diag2Id(int r, char c) {
		return r - colToIndex(c) + 8;
	}
	
	public static int rId(int d1, int d2) {
		return (d2 + d1 - 7) / 2;
	}
	
	public static char cId(int d1, int d2) {
		return indexToCol((d1 - d2 + 9) / 2);
	}

}
