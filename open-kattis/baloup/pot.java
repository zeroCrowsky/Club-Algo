import java.io.BufferedInputStream;
import java.util.Scanner;

public class pot {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int N = in.nextInt();
		int s = 0;
		for (int i = 0; i < N; i++) {
			int P = in.nextInt();
			s += (int) Math.pow(P/10, P%10);
		}
		System.out.println(s);
	}
	
	

}
