import java.io.BufferedInputStream;
import java.util.Scanner;

public class datum {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	static int[] dayShift = {3, 6, 6, 2, 4, 0, 2, 5, 1, 3, 6, 1};
	static String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

	public static void main(String[] args) {
		int D = in.nextInt(), M = in.nextInt();
		D = (D + dayShift[M - 1] - 1) % 7;
		System.out.println(weekDays[D]);
	}
	
	

}
