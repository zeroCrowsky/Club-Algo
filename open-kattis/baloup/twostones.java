import java.io.BufferedInputStream;
import java.util.Scanner;

public class twostones {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		long n = in.nextLong();
		System.out.println(n % 2 == 0 ? "Bob" : "Alice");
	}

}
