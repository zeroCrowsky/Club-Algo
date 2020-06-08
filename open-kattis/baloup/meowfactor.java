import java.io.BufferedInputStream;
import java.util.Scanner;

public class meowfactor {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	// tl;dr : biggest m with n % (m^9) == 0
	public static void main(String[] args) {
		// n < 2^63  =>  m^9 < 2^63  =>  m < 128
		for (long n = in.nextLong(), m = 128; m > 0; m--) {
			if (n % pow9(m) == 0) {
				System.out.println(m);
				return;
			}
		}
	}
	
	// pow function using only long to avoid rounding problem
	static long pow9(long m) {
		m *= m * m; // m^3
		return m * m * m; // (m^3)^3 == m^9
	}
	
	
}
