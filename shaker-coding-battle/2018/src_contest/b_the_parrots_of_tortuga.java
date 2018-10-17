import java.util.Scanner;

public class b_the_parrots_of_tortuga {
	
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		int G = in.nextInt(); // nb piece possede
		int N = in.nextInt();
		
		String bestName = null;
		int bestNb = 0;
		
		for (int i = 0; i < N; i++) {
			String S = in.next(); // nom dresseur
			int P = in.nextInt(); // prix / perroquet
			int C = in.nextInt(); // prix comission
			
			int nbP = (G - C) / P;
			
			if (nbP > bestNb && nbP > 0) {
				bestNb = nbP;
				bestName = S;
			}
		}
		
		System.out.println(bestName != null ? (bestNb + "\n" + bestName) : "Impossible");
	}

}
