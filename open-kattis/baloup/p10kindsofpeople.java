import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class p10kindsofpeople {
	static Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
	
	static int r, c;
	static boolean[][] zones;
	static int[][] regions;
	
	
	public static void main(String[] args) throws IOException {
		PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.out), 1 << 16));
		
		r = in.nextInt();
		c = in.nextInt();
		zones = new boolean[r][c];
		regions = new int[r][c]; 
		
		for (int i = 0; i < r; i++) {
			String row = in.next();
			for (int j = 0; j < c; j++)
				zones[i][j] = row.charAt(j) == '1';
		}

		
		int current0Region = 0;
		int current1Region = 0;

		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (regions[i][j] == 0) {
					fill(zones[i][j], i, j, zones[i][j] ? (++current1Region) : (--current0Region));
				}
			}
		}
		
		int n = in.nextInt();
		for (int i = 0; i < n; i++) {
			int r1 = in.nextInt() - 1, c1 = in.nextInt() - 1,
				r2 = in.nextInt() - 1, c2 = in.nextInt() - 1;
			if (regions[r1][c1] != regions[r2][c2])
				out.println("neither");
			else if (regions[r1][c1] < 0)
				out.println("binary");
			else
				out.println("decimal");
		}
		
		
		out.close();
	}
	
	// st is used to limit the stack size, since the algorith is deep first,
	// and could lead to a StackOVerflowError for a 1000x1000 space to fill
	static void fill(boolean zoneToFill, int i, int j, int currentRegion) {
		if (regions[i][j] != 0 || zones[i][j] != zoneToFill)
			return;
		
		regions[i][j] = currentRegion;
		if (i > 0)
			fill(zoneToFill, i-1, j, currentRegion);
		if (j > 0)
			fill(zoneToFill, i, j-1, currentRegion);
		if (i < r - 1)
			fill(zoneToFill, i+1, j, currentRegion);
		if (j < c - 1)
			fill(zoneToFill, i, j+1, currentRegion);
	}
	
	
	
}
