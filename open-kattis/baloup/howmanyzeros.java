import java.io.BufferedInputStream;
import java.util.Scanner;

public class howmanyzeros {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		for (;;) {
			long m = in.nextLong(), n = in.nextLong();
			if (m == -1 || n == -1)
				return;
			
			long count = 0;
			for (long i = m; i <= n; i++) {
				long ii = i;
				while (ii > 0) {
					if (ii % 10 == 0) {
						count++;
					}
					ii /= 10;
				}
			}
			if (m == 0)
				count++;
			System.out.println(count);
		}
	}
	
	

}
