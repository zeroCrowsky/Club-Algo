import java.io.BufferedInputStream;
import java.util.Scanner;

public class pet {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int bestI = -1, bestS = -1;
		for (int i = 1; i <= 5; i++) {
			int s = in.nextInt() + in.nextInt() + in.nextInt() + in.nextInt();
			if (s > bestS) {
				bestS = s;
				bestI = i;
			}
		}
		System.out.println(bestI + " " + bestS);
	}
	
	

}
