import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class c_sea_storm {
	
	static Scanner in = new Scanner(System.in);
	
	static Set<Integer> positionsTakenIndex = new HashSet<Integer>();
	
	static int L, N;
	static int[] P, H;
	
	static boolean oneIsOk = false;
	
	public static void main(String[] args) {
		L = in.nextInt(); // longueur pont
		N = in.nextInt(); // nb pirate

		P = new int[N]; // positions postes combat
		H = new int[N]; // taille chapeau (rayon, centre exclu)

		for (int i = 0; i < N; i++) {
			P[i] = in.nextInt() - 1;
		}
		
		for (int i = 0; i < N; i++) {
			H[i] = in.nextInt();
		}
		
		Arrays.sort(H);
		
		if (mainOld()) {
			System.out.println("YES");
			return;
		}
		

		List<Integer> current = new ArrayList<>();
		List<Integer> remaining = new ArrayList<>();
		
		
		for (int i = 0; i < N; i++) {
			remaining.add(i);
		}
		
		
		arrangementIterate(current, remaining);
		
		System.out.println(oneIsOk ? "YES" : "NO");
	}
	
	
	

	public static void arrangementIterate(List<Integer> current, List<Integer> remaining) {
		if (remaining.isEmpty()) {
			arrangementTest(current);
			return;
		}
		
		
		for (int i = 0; i < remaining.size() && !oneIsOk; i++) {
			int v = remaining.remove((int)i);
			current.add(v);
			arrangementIterate(current, remaining);
			current.remove(current.size() - 1);
			remaining.add(i, v);
		}
	}
	
	
	
	public static void arrangementTest(List<Integer> current) {
		
		boolean[] covered = new boolean[L];
		
		
		for (int h : H) {
			for (int p : current) {
				
				for (int i = Math.max(0, P[p] - h); i <= P[p] + h && i < L; i++) {
						covered[i] = true;
				}
				
				
			}
		}
		

		for (boolean c : covered) {
			if (!c) {
				return;
			}
		}
		
		oneIsOk = true;
		
		
	}
	
	
	
	
	
	
	
	public static boolean mainOld() {
		
		
		boolean[] covered = new boolean[L];
		
		for (int i = 0; i < N; i++) {
			covered[P[i]] = true;
		}
		
		int[] spaceWithPreview = new int[N];
		int[] spaceWithNext = new int[N];
		
		for (int i = 0; i < N; i++) {
			spaceWithPreview[i] = i > 0 ? (P[i] - P[i-1] - 1) : P[i];
				
			spaceWithNext[i] = (i < N - 1) ? (P[i+1] - P[i] - 1) : (L - P[i] - 1);
			
			//System.err.println(i);
			//System.err.println(spaceWithPreview[i] + " " + spaceWithNext[i]);
		}
		
		
		
		
		for (int hPos = N - 1; hPos >= 0; hPos--) {
			int h = H[hPos]; // taille chapeau courant
			
			int p = searchMaxSpaceIndex(spaceWithPreview, spaceWithNext);
			//System.err.println(p);
			positionsTakenIndex.add(p);
			
			for (int i = Math.max(0, P[p] - h); i <= P[p] + h && i < L; i++) {
					covered[i] = true;
			}
			
			// update spaces data
			for (int i = 0; i < N; i++) {
				int pPrev = i > 0 ? P[i-1] : -1;
				int pCurr = P[i];
				int pNext = i < N - 1 ? P[i+1] : L;
				
				spaceWithPreview[i] = 0;
				spaceWithNext[i] = 0;

				for (int pp = pPrev + 1; pp < pCurr; pp++) {
					if (!covered[pp])
						spaceWithPreview[i]++;
				}
				for (int pp = pCurr + 1; pp < pNext; pp++) {
					if (!covered[pp])
						spaceWithNext[i]++;
				}
			}
			
			
			
			
		}
		
		
		for (boolean c : covered) {
			//System.err.println(c);
			if (!c) {
				return false;
			}
		}
		
		return true;
		
		
		
		
		
	}
	
	
	
	
	static int searchMaxSpaceIndex(int[] spaceWithPreview, int[] spaceWithNext) {
		int max = -1;
		int minMax = -1;
		int maxIndex = -1;
		for (int i = 0; i < spaceWithPreview.length; i++) {
			if (positionsTakenIndex.contains(i))
				continue;

			int vm = Math.min(spaceWithPreview[i], spaceWithNext[i]);
			int vM = Math.max(spaceWithPreview[i], spaceWithNext[i]);
			if (vM > max) {
				max = vM;
				minMax = vm;
				maxIndex = i;
			}
			else if (vM == max && vm > minMax) {
				max = vM;
				minMax = vm;
				maxIndex = i;
			}
		}
		
		return maxIndex;
	}

}
