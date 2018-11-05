import java.io.BufferedInputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class sumoftheothers {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		try {
			for(;;) {
				int sum = 0;
				for (String s : in.nextLine().split(" ")) {
					sum += Integer.parseInt(s);
				}
				
				System.out.println(sum / 2);
			}
		} catch(NoSuchElementException e) {
			
		}
	}

}
