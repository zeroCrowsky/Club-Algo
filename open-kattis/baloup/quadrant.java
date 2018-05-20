import java.util.Scanner;

// https://open.kattis.com/problems/quadrant
public class quadrant {

	
	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			int x = in.nextInt(), y = in.nextInt();
			System.out.println(y > 0 ? (x > 0 ? 1 : 2) : (x > 0 ? 4 : 3));
		}
	}
	
	
}
