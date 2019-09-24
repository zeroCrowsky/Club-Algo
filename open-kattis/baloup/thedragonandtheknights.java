import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Not working
 * Uncomment debug print and graphically represent problem to understand why
 */
public class thedragonandtheknights {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int T = in.nextInt();
		for (int i = 0; i <= T; i++) { new thedragonandtheknights(); }
	}
	
	
	
	
	int n, m;
	River[] rivers;
	
	
	thedragonandtheknights() {
		n = in.nextInt();
		m = in.nextInt();
		rivers = new River[n];
		for (int i = 0; i < n; i++) {
			rivers[i] = new River(in.nextInt(), in.nextInt(), in.nextInt());
		}
		List<Knight> knights = new ArrayList<>(m);
		for (int i = 0; i < m; i++) {
			knights.add(new Knight(in.nextInt(), in.nextInt()));
		}
		
		boolean result = recur(0, knights);
		
		System.out.println(result ? "PROTECTED" : "VULNERABLE");
		
	}
	
	
	boolean recur(int currIndex, List<Knight> knights) {
		if (currIndex >= rivers.length)
			return true;
		// System.out.println("recur(" + currIndex + ") " + rivers[currIndex]);
		Map<Boolean, List<Knight>> subLists = knights.stream()
				.collect(Collectors.partitioningBy(k -> rivers[currIndex].side(k)));
		// System.out.println(subLists.get(true).size() + " " + subLists.get(false).size());
		if (subLists.get(true).isEmpty() || subLists.get(false).isEmpty())
			return false;
		return recur(currIndex + 1, subLists.get(true))
				&& recur(currIndex + 1, subLists.get(false));
	}
	
	
	
	static class River {
		int a, b, c;
		public River(int A, int B, int C) {
			a = A;
			b = B;
			c = C;
		}
		
		boolean side(Knight k) {
			return (a * k.x + b * k.y + c) / Math.sqrt(a * a + b * b) > 0;
		}
		
		@Override
		public String toString() {
			return a + "x+" + b + "y+" + c + "=0";
		}
		
	}
	
	static class Knight {
		int x, y;
		public Knight(int X, int Y) {
			x = X;
			y = Y;
		}
	}
}
