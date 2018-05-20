import java.io.BufferedInputStream;
import java.util.Scanner;

public class r2 {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// S = (R1 + R2) / 2
		// S * 2 = R1 + R2
		// S * 2 - R1 = R2
		int R1 = in.nextInt(), S = in.nextInt();
		System.out.println(S * 2 - R1);
	}

}
