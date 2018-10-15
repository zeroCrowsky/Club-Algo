import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class boatparts {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	

	public static void main(String[] args) {
		int P = in.nextInt();
		int N = in.nextInt();
		Set<String> W = new HashSet<>();
		
		for (int i = 0; i < N; i++) {
			W.add(in.next());
			if (W.size() == P) {
				System.out.println(i+1);
				return;
			}
		}
		
		System.out.println("paradox avoided");
		
		
		
		
		
	}

}
