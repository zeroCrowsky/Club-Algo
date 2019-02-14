import java.io.BufferedInputStream;
import java.util.Scanner;

public class compass {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int n1 = in.nextInt(), n2 = in.nextInt();
		
		int cw = n2 - n1;
		if (cw < 0)
			cw += 360;
		int ccw = n1 - n2;
		if (ccw < 0)
			ccw += 360;
		
		System.out.println(Math.abs(cw) <= Math.abs(ccw) ? cw : ("-" + ccw));
	}

}
