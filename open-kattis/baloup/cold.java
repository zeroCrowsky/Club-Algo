import java.io.BufferedInputStream;
import java.util.Scanner;

public class cold {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		int n = in.nextInt();
		int count = 0;
		for (int i = 0; i < n; i++)
			if (in.nextInt() < 0) count++;
		System.out.println(count);
	}

}
