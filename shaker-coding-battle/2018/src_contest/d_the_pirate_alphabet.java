import java.util.Scanner;

public class d_the_pirate_alphabet {
	
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		String finalString = "aeiouybcdfghjklmnpqrstvwxz";
		
		
		String initial = in.next();
		
		
		
		System.out.println(compute(initial, finalString));
	}
	
	
	
	
	
	
	
	public static <T> int compute(String o1, String o2) {
		
		int[] prev = new int[o2.length() + 1];

		for (int j = 0; j < o2.length() + 1; j++) {
			prev[j] = j;
		}

		for (int i = 1; i < o1.length() + 1; i++) {

			int[] curr = new int[o2.length() + 1];
			// curr[0] = i;

			for (int j = 1; j < o2.length() + 1; j++) {
				int d1 = prev[j];
				int d2 = curr[j - 1] + 1;
				int d3 = prev[j - 1] + (o1.charAt(i - 1) == o2.charAt(j - 1) ? 0 : 1);
				curr[j] = Math.min(Math.min(d1, d2), d3);
			}

			prev = curr;
		}
		return prev[o2.length()];
	}
	
	
	
	

}
