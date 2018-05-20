import java.util.Scanner;

public class simon {

	private static Scanner in;
	static final String simonSays = "simon says ";

	public static void main(String[] args) {
		in = new Scanner(System.in);
		int nb = in.nextInt();
		in.nextLine();
		for (int i=0; i<nb; i++) {
			String line = in.nextLine();
			System.out.println(line.startsWith(simonSays) ? line.substring(simonSays.length()) : "");
		}
		
		
	}

}
