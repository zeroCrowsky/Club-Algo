import java.util.Scanner;

public class a_loot_sharing {
	
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {

		int B = in.nextInt();
		int T = in.nextInt();
		int N = in.nextInt();
		
		
		System.out.println((B * 2 + T * 3 <= N) ? "LOOT!" : "RHUM!");
		
	}

}
