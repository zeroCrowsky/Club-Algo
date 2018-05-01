package r1a_p3_edgy_baking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Not working !!! (only correct for examples)
 */
public class Solution {
	
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			int T = s.nextInt();
			for (int t = 0; t < T; t++) {
				
				int N = s.nextInt();
				int P = s.nextInt();

				List<Cookie> cookies = new ArrayList<>(N);
				List<Boolean> dontCut = new ArrayList<>(N);
				
				for (int n = 0; n < N; n++) {
					cookies.add(new Cookie(s.nextInt(), s.nextInt()));
					dontCut.add(false);
				}
				
				double currentP = recursive(cookies, dontCut, P);
				
				System.out.println("Case #" + (t + 1) + ": " + currentP);
				
				
				
				
			}
		}
	}
	
	// return best P
	static double recursive(List<Cookie> cookies, List<Boolean> dontCut, int P) {
		double currentP = currentPerimeter(cookies);
		
		boolean lastPerfect = cleanCuttable(cookies, dontCut, currentP, P);
		if (lastPerfect) {
			return P;
		}

		double sumMinAdded = sumMinAdded(cookies, dontCut);
		double sumMaxAdded = sumMaxAdded(cookies, dontCut);
		
		if (sumMaxAdded + currentP <= P)
			return sumMaxAdded + currentP;
		else if (sumMaxAdded + currentP > P && sumMinAdded + currentP <= P)
			return P;
		else {
			double bestP = currentP;
			for (int i = 0; i < cookies.size(); i++) {
				if (dontCut.get(i))
					continue;
				dontCut.set(i, true);
				double newP = recursive(cookies, dontCut, P);
				dontCut.set(i, false);
				if (newP > bestP)
					bestP = newP;
			}
			
			return bestP;
		}
	}
	
	/*
	 * @return : si le dernier de la liste permet d'avoir exactement P
	 */
	static boolean cleanCuttable(List<Cookie> cuttables, List<Boolean> dontCut, double currentP, int P) {
		cuttables.sort((c1, c2) -> Double.compare(c1.minAdded, c2.minAdded));
		
		for (int i = cuttables.size() - 1; i >= 0; i--) {
			if (dontCut.get(i))
				continue;
			Cookie c = cuttables.get(i);
			if (c.minAdded + currentP > P) {
				dontCut.set(i, true);
				continue;
			}
			if (c.minAdded + currentP <= P && c.maxAdded + currentP >= P) {
				return true;
			}
		}
		
		return false;
	}
	
	
	static double currentPerimeter(List<Cookie> cookies) {
		double sum = 0;
		for (Cookie c : cookies) {
			sum += c.perimeter;
		}
		return sum;
	}

	
	static double sumMinAdded(List<Cookie> cookies, List<Boolean> dontCut) {
		double sum = 0;
		for (int i = 0; i < cookies.size(); i++) {
			if (dontCut.get(i))
				continue;
			sum += cookies.get(i).minAdded;
		}
		return sum;
	}
	
	static double sumMaxAdded(List<Cookie> cookies, List<Boolean> dontCut) {
		double sum = 0;
		for (int i = 0; i < cookies.size(); i++) {
			if (dontCut.get(i))
				continue;
			sum += cookies.get(i).maxAdded;
		}
		return sum;
	}
	
	
	
	
	private static class Cookie {
		int W, H;
		double perimeter, minAdded, maxAdded;
		
		public Cookie(int w, int h) {
			W = w;
			H = h;
			perimeter = (W + H) * 2;
			minAdded = 2 * Math.min(W, H);
			maxAdded = 2 * Math.sqrt(W*W + H*H);
		}
		
	}
	
}
