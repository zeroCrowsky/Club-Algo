import java.io.BufferedInputStream;
import java.util.Scanner;

public class lastfactorialdigit {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	static int[] res = {1, 2, 6, 4};

	public static void main(String[] args) {
		int T = in.nextInt();
		
		for (int t = 0; t < T; t++) {
			int N = in.nextInt();
			System.out.println(N < 5 ? res[N - 1] : 0);
		}
	}
	
}
