import java.io.BufferedInputStream;
import java.util.Scanner;

public class batterup {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		int N = in.nextInt();
		int sum = 0; int count = 0;
		for (int i = 0; i < N; i++) {
			int b = in.nextInt();
			if (b == -1) continue;
			count++;
			sum += b;
		}
		System.out.println(sum / (double) count);
	}

}
