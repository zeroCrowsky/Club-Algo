import java.io.BufferedInputStream;
import java.util.Scanner;

public class fizzbuzz {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int X = in.nextInt();
		int Y = in.nextInt();
		int N = in.nextInt();
		
		for (int i = 1; i <= N; i++) {
			boolean dX = i % X == 0, dY = i % Y == 0;
			System.out.println(dX && dY ? "FizzBuzz" : dX ? "Fizz" : dY ? "Buzz" : i);
		}
		
	}
	
	

}
