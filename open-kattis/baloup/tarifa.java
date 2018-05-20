import java.io.BufferedInputStream;
import java.util.Scanner;

public class tarifa {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		int X = in.nextInt(), N = in.nextInt();
		int curr = 0;
		for (int i = 0; i < N; i++) {
			curr += X - in.nextInt();
		}
		System.out.println(curr + X);
	}

}
