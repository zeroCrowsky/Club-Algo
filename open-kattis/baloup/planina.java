import java.io.BufferedInputStream;
import java.util.Scanner;

public class planina {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		int N = in.nextInt();
		// 1 -> (2^1 + 1)² = 9
		// 2 -> (2^2 + 1)² = 25
		// 3 -> (2^3 + 1)² = 81
		// 4 -> (2^4 + 1)² = ...
		System.out.println((int) Math.pow(((int)Math.pow(2, N)) + 1, 2));
	}

}
