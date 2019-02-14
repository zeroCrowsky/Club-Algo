import java.awt.Point;
import java.io.BufferedInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TSPOld {
	
	static class Entry implements Comparable<Entry> {
		/*
		 * Key part
		 */
		BitSet set;
		int first;
		public Entry(BitSet s, int f) {
			set = s;
			first = f;
		}
		@Override
		public boolean equals(Object o) {
			if (o == null || !(o instanceof Entry))
				return false;
			Entry obj = (Entry) o;
			return Objects.equals(set, obj.set) && first == obj.first;
		}
		@Override
		public int hashCode() {
			return Objects.hash(set, first);
		}
		@Override
		public int compareTo(Entry o) {
			int c;
			/*long[] longs = set.toLongArray();
			long[] oLongs = o.set.toLongArray();
			for (int i = Math.min(longs.length, oLongs.length) - 1; i >= 0; i--)
				if ((c = Long.compareUnsigned(longs[i], oLongs[i])) != 0)
					return -c; // reverse order for the BitSet */
			if ((c = compareBitSet(set, o.set)) != 0)
				return -c; // reverse order for the BitSet
			return Integer.compare(first, o.first);
		}
		
		/*
		 * Value part
		 */
		
		float cost;
		int[] list;
		
		public Entry(BitSet s, int f, float c, int[] l) {
			this(s, f);
			cost = c;
			list = l;
		}
		
		public Entry(BitSet s, int f, float c, int insertedInList, int[] l) {
			this(s, f, c, new int[l.length + 1]);
			System.arraycopy(l, 0, list, 1, l.length);
			list[0] = insertedInList;
		}

	}
	
	private static Field arrayBitSetField;
	
	static int compareBitSet(BitSet lhs, BitSet rhs) {
		try {
			int c;
			long[] longs = (long[]) arrayBitSetField.get(lhs);
			long[] oLongs = (long[]) arrayBitSetField.get(rhs);
			for (int i = Math.min(longs.length, oLongs.length) - 1; i >= 0; i--)
				if ((c = Long.compareUnsigned(longs[i], oLongs[i])) != 0)
					return c; // reverse order for the BitSet
			return 0;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		/*
	    if (lhs.equals(rhs))
	    	return 0;
	    BitSet xor = (BitSet)lhs.clone();
	    xor.xor(rhs);
	    int firstDifferent = xor.length()-1;
	    if(firstDifferent == -1)
	    	return 0;
	    return rhs.get(firstDifferent) ? 1 : -1;
		*/
	}
	
	
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	static int n;
	
	static int BEST;
	
	static float[][] D;
	
	
	static float bestSolScore = Float.MAX_VALUE;
	
	static long lastPrint = System.currentTimeMillis();
	
	
	public static void main(String[] args) throws Exception {

		arrayBitSetField = BitSet.class.getDeclaredField("words");
		arrayBitSetField.setAccessible(true);
		
		if (in.next().equals("matrix")) {
			n = in.nextInt();
			BEST = in.nextInt();
			D = new float[n][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					D[i][j] = Float.parseFloat(in.next());
		}
		else {
			n = in.nextInt();
			BEST = in.nextInt();
			D = new float[n][n];
			Point[] pts = new Point[n];
			for (int i = 0; i < n; i++) {
				in.nextInt();
				pts[i] = new Point(in.nextInt(), in.nextInt());
			}

			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					D[i][j] = (float) pts[i].distance(pts[j]);
			
		}
		
		dpBase();
		
	}
	

	public static List<Entry> sMinus1Cost;
	public static List<Entry> sCost;

	public static float sMinus1CostMin = 0;
	public static float sCostMin = Float.MAX_VALUE;
	public static float sCostMax = 0;
	
	static long nbPassedEntry, nbSavedEntry;

	// avoid a lot of object instantiation
	static int[] buffSet;
	static int[] buffSubSet;
	static Entry buffSearchKey = new Entry(null, 0);
	
	public static void dpIt(int maxSize, int currSize, BitSet current, int currPos) {
		if (currSize == maxSize) {
			BitSet currentClone = (BitSet)current.clone();
			if (maxSize == 2) {
				int v1 = current.nextSetBit(0);
				int v2 = current.nextSetBit(v1 + 1);
				float cost1 = D[v1][v2] + D[v2][0];
				float cost2 = D[v2][v1] + D[v1][0];
				nbPassedEntry += 2;
				nbSavedEntry += 2;
				sCost.add(new Entry(currentClone, v1, cost1, new int[] {v1, v2}));
				sCost.add(new Entry(currentClone, v2, cost2, new int[] {v2, v1}));
				sCostMin = Math.min(sCostMin, Math.min(cost1, cost2));
				sCostMax = Math.max(sCostMax, Math.max(cost1, cost2));
			}
			else {
				BitSet subset = (BitSet)current.clone();
				bitSetToArray(currentClone, buffSet);
				for (int i = 0; i < buffSet.length; i++) {
					int v = buffSet[i];
					subset.clear(v);
					float currentBest = Float.MAX_VALUE;
					int[] currentBestList = null;
					bitSetToArray(subset, buffSubSet);
					for (int j = 0; j < buffSubSet.length; j++) {
						int vSub = buffSubSet[j];
						buffSearchKey.set = subset;
						buffSearchKey.first = vSub;
						int entryI = Collections.binarySearch(sMinus1Cost, buffSearchKey);
						Entry sMinus1 = entryI >= 0 ? sMinus1Cost.get(entryI) : null;
						if (sMinus1 == null)
							continue;
						float cost = D[v][vSub] + sMinus1.cost;
						sCostMax = Math.max(sCostMax, cost);
						if (maxSize < n / 2 - 1 && cost > BEST - sMinus1CostMin + 1)
							continue;
						if (cost > BEST)
							continue;
						if (cost < currentBest) {
							currentBest = cost;
							currentBestList = sMinus1.list;
						}
					}

					subset.set(v); // restoring subset state for next loop
					
					nbPassedEntry++;
					
					if (currentBestList != null) {
						nbSavedEntry++;
						sCost.add(new Entry(currentClone, v, currentBest, v, currentBestList));
						sCostMin = Math.min(sCostMin, currentBest);
					}
				}
				if (lastPrint < System.currentTimeMillis() - 10000) {
					System.out.println("(i=" + maxSize + "; SaveRatio=" + nbSavedEntry + "/" + nbPassedEntry + "; MaxCost=" + sCostMax + ") Current bitset: " + toStringBitSet(current, n));
					lastPrint = System.currentTimeMillis();
				}
			}
			return;
		}
		
		
		for (int i = currPos; i > 0; i--) {
			current.set(i);
			dpIt(maxSize, currSize + 1, current, i - 1);
			current.clear(i);
		}
	}
	
	
	
	public static void dpBase() {
		for (int i = 2; i < n; i++) {
			sMinus1Cost = sCost;
			sMinus1CostMin = sCostMin;
			sCost = new ArrayList<>(10000000);
			sCostMin = Float.MAX_VALUE;
			sCostMax = 0;
			nbPassedEntry = nbSavedEntry = 0;
			buffSet = new int[i];
			buffSubSet = new int[i - 1];
			dpIt(i, 0, new BitSet(n), n - 1);
			// sCost.sort(null);
			System.out.println("DP i=" + i + ": " + sCost.size() + " values stored. (SaveRatio=" + nbSavedEntry + "/" + nbPassedEntry + "; MinCost=" + sCostMin + "; CostMaxThreshold=" + (BEST - sCostMin + 1) + "; MaxCost=" + sCostMax + ")");
		}
		
		Entry best = null;
		
		for (Entry e : sCost) {;
			float cost = D[0][e.first] + e.cost;
			if (best == null || cost < best.cost) {
				best = new Entry(null, 0, cost, 0, e.list);
			}
		}
		
		print(Arrays.stream(best.list).boxed().collect(Collectors.toList()), best.cost);
	}
	
	
	
	
	static void print(List<Integer> current, float s) {
		System.out.println("Solution(" + s + "):");
		for (int i = 0; i < current.size(); i++) {
			System.out.print(current.get(i) + " ");
		}
		System.out.println();
	}
	
	static String toStringBitSet(BitSet set, int s) {
		char[] chars = new char[s];
		for (int i = 0; i < s; i++)
			chars[i] = set.get(i) ? '1' : '0';
		return new String(chars);
	}
	
	static int bitSetToArray(BitSet set, int[] buff) {
		int iSet = -1, iBuff = 0;
		while ((iSet = set.nextSetBit(iSet + 1)) >= 0 && iBuff < buff.length)
			buff[iBuff++] = iSet;
		return iBuff;
	}
	
}
