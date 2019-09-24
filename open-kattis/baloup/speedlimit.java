import java.io.BufferedInputStream;
import java.util.Scanner;

public class speedlimit {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		
		for(;;) {
			int N = in.nextInt();
			if (N == -1)
				break;
			int prevT = 0;
			int sum = 0;
			
			for (int i = 1; i <= N; i++) {
				int s = in.nextInt(), t = in.nextInt();
				sum += s * (t - prevT);
				prevT = t;
			}
			
			System.out.println(sum + " miles");
		}
		
	}
	
	

}
