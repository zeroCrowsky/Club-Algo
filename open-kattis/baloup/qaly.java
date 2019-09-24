import java.io.BufferedInputStream;
import java.util.Scanner;

public class qaly {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int N = in.nextInt();
		
		float s = 0;
		
		for (int i = 0; i < N; i++) {
			s += Float.parseFloat(in.next()) * Float.parseFloat(in.next());
		}
		
		System.out.println(s);
		
	}
	
	

}
