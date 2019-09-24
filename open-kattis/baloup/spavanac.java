import java.io.BufferedInputStream;
import java.util.Scanner;

public class spavanac {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int H = in.nextInt(), M = in.nextInt();
		H = M >= 45 ? H      : H == 0 ? 23 : H - 1;
		M = M >= 45 ? M - 45 : M + 15;
		System.out.println(H + " " + M);
	}
	
	

}
