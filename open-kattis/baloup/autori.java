import java.io.BufferedInputStream;
import java.util.Scanner;

public class autori {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		for (char c : in.next().toCharArray())
			if (c >= 'A' && c <= 'Z')
				System.out.print(c);
		System.out.println();
	}

}
