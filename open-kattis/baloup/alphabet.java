import java.io.BufferedInputStream;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.ToIntBiFunction;

public class alphabet {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		String s = in.next();
		String a = "abcdefghijklmnopqrstuvwxyz";
		
		System.out.println(new LevenshteinDistance(s, a, 1, 0, (c1, c2) -> c1.equals(c2) ? 0 : 1).getCurrentDistance());
		
	}
	
	
	
	

	public static class LevenshteinDistance {
		
		private String initialList;
	
		private int elementAdditionScore;
		private int elementDeletionScore;
		
		private ToIntBiFunction<Character, Character> elementDistanceFunction;
		
		private int[] prev, curr; 
		
		private int progress = 0;
		
		public LevenshteinDistance(String initList, String finList, int addScore, int delScore, ToIntBiFunction<Character, Character> elemDistFn) {
			initialList = initList == null ? "" : initList;
			elementAdditionScore = addScore;
			elementDeletionScore = delScore;
			elementDistanceFunction = elemDistFn == null ? ((e1, e2) -> Objects.equals(e1, e2) ? 0 : 1) : elemDistFn;
	
			prev = new int[initialList.length() + 1];
			
			curr = new int[initialList.length() + 1];
			for (int i = 0; i < curr.length; i++)
				curr[i] = i * elementDeletionScore;
			
			add(finList);
		}
		
		public LevenshteinDistance() {
			this(null, null, 1, 1, null);
		}
		
		public LevenshteinDistance(String initList) {
			this(initList, null, 1, 1, null);
		}
		
		public int getCurrentDistance() {
			return curr[curr.length - 1];
		}
		
		public void add(String els) {
			for (char el : els.toCharArray())
				add(el);
		}
		
		public void add(char el) {
			progress++;
			// swap score arrays
			int[] tmp = prev; prev = curr; curr = tmp;
			
			curr[0] = progress * elementAdditionScore;
			
			for (int i = 1; i < curr.length; i++) {
				int S = prev[i - 1] + elementDistanceFunction.applyAsInt(initialList.charAt(i - 1), el);
				int A = prev[i] + elementAdditionScore;
				int D = curr[i - 1] + elementDeletionScore;
				curr[i] = Math.min(S, Math.min(A, D));
			}
		}
		
		
		
	}

	
	

}
