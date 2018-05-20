import java.io.BufferedInputStream;
import java.util.Scanner;

public class faktor {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		int A = in.nextInt(), I = in.nextInt();
		System.out.println(A * I - A + 1);
	}

}
