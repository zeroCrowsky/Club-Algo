import java.io.BufferedInputStream;
import java.util.Scanner;

public class sibice {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int N = in.nextInt();
		int W = in.nextInt();
		int H = in.nextInt();
		double d = Math.sqrt(W*W + H*H);
		
		for (int i = 0; i < N; i++) {
			System.out.println(in.nextInt() > d ? "NE" : "DA");
		}
		
	}
	
	

}
