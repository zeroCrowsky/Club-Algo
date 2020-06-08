import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class everywhere {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int T = in.nextInt();
		
		for (int t = 0; t < T; t++) {
			int N = in.nextInt();
			
			Set<String> cities = new HashSet<>();
			
			for (int i = 0; i < N; i++) {
				cities.add(in.next());
			}
			
			System.out.println(cities.size());
			
			
		}
	}
	
}
