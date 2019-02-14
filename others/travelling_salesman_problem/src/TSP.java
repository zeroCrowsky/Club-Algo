
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class TSP {
	
	
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
		
		
		public long[] getBitSetArray() {
			if (set == null)
				return null;
			try {
				return (long[])arrayBitSetField.get(set);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public void setBitSetArray(long[] arr) {
			if (set == null) {
				set = BitSet.valueOf(arr);
				return;
			}
			
			int wiu = arr.length;
			while(wiu > 0 && arr[wiu - 1] == 0)
				wiu--;
			
			try {
				arrayBitSetField.set(set, arr);
				wiuBitSetField.set(set, wiu);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		public void update(BitSet s, int f, float c) {
			set = s;
			first = f;
			cost = c;
		}
		
		public void update(BitSet s, int f, float c, int[] l) {
			update(s, f, c);
			list = l;
		}
		
		public void update(BitSet s, int f, float c, int insertedInList, int[] l) {
			update(s, f, c);
			if (list.length != l.length + 1)
				list = new int[l.length + 1];
			System.arraycopy(l, 0, list, 1, l.length);
			list[0] = insertedInList;
		}
		
		
		public void fromBuffer(ByteBuffer buff) {
			buff.position(0);
			int sLength = buff.getInt();
			long[] currS = getBitSetArray();
			long[] s = currS != null && currS.length == sLength ? currS : new long[sLength];
			for (int i = 0; i < s.length; i++) {
				s[i] = buff.getLong();
			}
			int f = buff.getInt();
			float c = buff.getFloat();
			int lLength = buff.getInt();
			int[] l = list != null && list.length == lLength ? list : new int[lLength];
			for (int i = 0; i < l.length; i++) {
				l[i] = buff.getInt();
			}
			
			setBitSetArray(s);
			update(set, f, c, l);
		}
		
		public void toBuffer(ByteBuffer buff) {
			buff.position(0);
			long[] s = getBitSetArray();
			buff.putInt(s.length);
			for (long l : s)
				buff.putLong(l);
			buff.putInt(first);
			buff.putFloat(cost);
			buff.putInt(list.length);
			for (int i : list)
				buff.putInt(i);
		}
		
		static int fileElementSize(int i) {
			return 4+((n-1)/64+1)*8+4+4+4+i*4;
		}
		
		public void toOutputStream(ByteBuffer buff, OutputStream out) {
			toBuffer(buff);
			try {
				out.write(buff.array());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		
		public String toString() {
			return "{set=" + toStringBitSet(set, n) +
					", first=" + first +
					", cost=" + cost +
					", list=" + Arrays.toString(list) + "}";
		}
	}
	
	private static Field arrayBitSetField;
	private static Field wiuBitSetField;
	
	static int compareBitSet(BitSet lhs, BitSet rhs) {
		try {
			int c;
			long[] longs = (long[]) arrayBitSetField.get(lhs);
			long[] oLongs = (long[]) arrayBitSetField.get(rhs);
			for (int i = Math.min(longs.length, oLongs.length) - 1; i >= 0; i--)
				if ((c = Long.compareUnsigned(longs[i], oLongs[i])) != 0)
					return c;
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

		wiuBitSetField = BitSet.class.getDeclaredField("wordsInUse");
		wiuBitSetField.setAccessible(true);
		
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
		

		//List<Integer> current = new ArrayList<>();
		//List<Integer> remaining = new ArrayList<>();

		//for (int i = 0; i < n; i++) {
		//	remaining.add(i);
		//}
		
		// recurArrangements(current, remaining);
		
		dpBase();
		
	}
	

	// public static List<Entry> sMinus1Cost;
	// public static List<Entry> sCost;

	public static float sMinus1CostMin = 0;
	public static float sCostMin = Float.MAX_VALUE;
	public static float sCostMax = 0;
	
	static long nbPassedEntry, nbSavedEntry, nbTotalEntry, entrySize, itStart;

	// avoid a lot of object instantiation
	static int[] buffSet;
	static int[] buffSubSet;
	static Entry buffSearchKey = new Entry(null, 0);
	static ByteBuffer buffFile = null;
	static Entry buffEntryFile = new Entry(null, 0);
	static OutputStream outFile;
	static ByteBuffer buffInFile = null;
	static Cache<Long, Entry> cacheInFile;
	static RandomAccessFile inFile;
	
	static Entry binarySearchInFile(Entry key, int i) throws IOException {
		//System.out.println("binarySearchInFile(key=" + key + ", i=" + i + ")");
		int elSize = Entry.fileElementSize(i);
		long low = 0;
		long high = inFile.length() / elSize - 1;
		//System.out.println("  elSize=" + elSize + "; low=0; high=" + high + ";");
		while (low <= high) {
			long mid = (low + high) >>> 1;
			
			Entry tmp = cacheInFile.getIfPresent(mid);
			if (tmp == null) {
				inFile.seek(mid * elSize);
				inFile.readFully(buffInFile.array(), 0, buffInFile.array().length);
				tmp = new Entry(null, 0);
				tmp.fromBuffer(buffInFile);
				cacheInFile.put(mid, tmp);
			}
			
			int cmp = tmp.compareTo(key);

			//System.out.println("    mid=" + mid + "; entryBuff=" + entryBuff + ";");
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else {
				//System.out.println("binarySearchInFile returns true");
				return tmp; // key found
			}
			//System.out.println("  low=" + low + "; high=" + high + ";");
		}
		//System.out.println("binarySearchInFile returns false");
		return null;  // key not found
	}
	
	public static void dpIt(int maxSize, int currSize, BitSet current, int currPos) {
		
		if (currSize == maxSize) {
			if (maxSize == 2) {
				int v1 = current.nextSetBit(0);
				int v2 = current.nextSetBit(v1 + 1);
				float cost1 = D[v1][v2] + D[v2][0];
				float cost2 = D[v2][v1] + D[v1][0];
				
				buffEntryFile.update(current, v1, cost1);
				buffEntryFile.list[0] = v1;
				buffEntryFile.list[1] = v2;
				buffEntryFile.toOutputStream(buffFile, outFile);
				nbSavedEntry++;
				nbPassedEntry++;
				buffEntryFile.update(current, v2, cost2);
				buffEntryFile.list[0] = v2;
				buffEntryFile.list[1] = v1;
				buffEntryFile.toOutputStream(buffFile, outFile);
				nbSavedEntry++;
				nbPassedEntry++;
				
				sCostMin = Math.min(sCostMin, Math.min(cost1, cost2));
				sCostMax = Math.max(sCostMax, Math.max(cost1, cost2));
			}
			else {
				BitSet currentClone = (BitSet)current.clone();
				bitSetToArray(currentClone, buffSet);
				for (int i = 0; i < buffSet.length; i++) {
					int v = buffSet[i];
					currentClone.clear(v);
					float currentBest = Float.MAX_VALUE;
					int[] currentBestList = null;
					bitSetToArray(currentClone, buffSubSet);
					for (int j = 0; j < buffSubSet.length; j++) {
						int vSub = buffSubSet[j];
						buffSearchKey.set = currentClone;
						buffSearchKey.first = vSub;
						Entry fromInFile;
						try {
							fromInFile = binarySearchInFile(buffSearchKey, maxSize - 1);
						} catch (IOException e) {
							throw new UncheckedIOException(e);
						}
						if (fromInFile == null)
							continue;
						float cost = D[v][vSub] + fromInFile.cost;
						sCostMax = Math.max(sCostMax, cost);
						if (maxSize < n / 2 && cost > BEST - sMinus1CostMin + 1)
							continue;
						if (cost > BEST)
							continue;
						if (cost < currentBest) {
							currentBest = cost;
							currentBestList = fromInFile.list;
						}
					}

					currentClone.set(v); // restoring subset state for next loop
					
					nbPassedEntry++;
					
					if (currentBestList != null) {
						nbSavedEntry++;
						buffEntryFile.update(currentClone, v, currentBest, v, currentBestList);
						buffEntryFile.toOutputStream(buffFile, outFile);
						sCostMin = Math.min(sCostMin, currentBest);
					}
				}
				if (lastPrint < System.currentTimeMillis() - 20000) {
					float porcentProgress = nbPassedEntry * 10000 / nbTotalEntry / 100f;
					long nbCancelled = nbPassedEntry - nbSavedEntry;
					long remainingEntry = nbTotalEntry - nbPassedEntry;
					float porcentCancelled = nbCancelled * 10000 / nbPassedEntry / 100f;
					long elapsedTime = System.currentTimeMillis() - itStart;
					int entrySize = buffFile.array().length;
					long estimatedFileSize = (long) (entrySize * nbTotalEntry * (nbSavedEntry / (float) nbPassedEntry));
					long estimatedRemainingTime = (long) (elapsedTime / (float)nbPassedEntry * remainingEntry);
					System.out.println("Iter. Progress: i=" + maxSize + ";"
							+ " Cancelled=" + nbCancelled + "(" + porcentCancelled + "%);"
							+ " Progress=" + nbPassedEntry + "/" + nbTotalEntry + "(" + porcentProgress + "%);"
							+ " FileSize(Curr=" + MemoryUtil.humanReadableSize(entrySize * nbSavedEntry) + "; Estim=" + MemoryUtil.humanReadableSize(estimatedFileSize) + ");"
							+ " ETA=" + TimeUtil.durationToString(estimatedRemainingTime) + ";"
							+ " MaxCost=" + sCostMax + ";"
							+ " CurrBitSet: " + toStringBitSet(current, n));
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
		int i = 2;
		File f;
		//while((f = new File("_dp_" + i + ".tmp")).exists() && f.length() == 0)
		//	i++;
		//i++;
		for (; i < n; i++) {
			// sMinus1Cost = sCost;
			sMinus1CostMin = sCostMin;
			// sCost = new ArrayList<>(10_000_000);
			sCostMin = Float.MAX_VALUE;
			sCostMax = 0;
			nbPassedEntry = nbSavedEntry = 0;
			nbTotalEntry = binCoeff(i, n-1) * i;
			itStart = System.currentTimeMillis();
			buffSet = new int[i];
			buffSubSet = new int[i - 1];
			buffFile = ByteBuffer.allocate(Entry.fileElementSize(i));
			buffInFile = ByteBuffer.allocate(Entry.fileElementSize(i-1));
			cacheInFile = CacheBuilder.newBuilder()
					.concurrencyLevel(1)
					.maximumSize(20_000_000)
					.initialCapacity(20_000_000)
					.build();
			buffEntryFile.list = new int[i];
			try (OutputStream out = new BufferedOutputStream(new FileOutputStream("_dp_" + i + ".tmp", false))) {
				outFile = out;
				if (i == 2) {
					dpIt(i, 0, new BitSet(n), n - 1);
				}
				else {
					try (RandomAccessFile in = new RandomAccessFile("_dp_" + (i - 1) + ".tmp", "rw")) {
						inFile = in;
						dpIt(i, 0, new BitSet(n), n - 1);
						inFile = null;
						in.setLength(0);
					}
				}
				outFile = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("DP i=" + i + " (SaveRatio=" + nbSavedEntry + "/" + nbPassedEntry + "; MinCost=" + sCostMin + "; CostMaxThreshold=" + (BEST - sMinus1CostMin + 1) + "; MaxCost=" + sCostMax + ")");
			long fSize = new File("_dp_" + i + ".tmp").length();
			System.out.println("File size = " + fSize + "");
		}
		
		Entry best = null;


		buffInFile = ByteBuffer.allocate(Entry.fileElementSize(i-1));
		buffEntryFile.list = new int[i-1];
		try (RandomAccessFile in = new RandomAccessFile("_dp_" + (i - 1) + ".tmp", "r")) {
			for (;;) {
				in.readFully(buffInFile.array());
				buffEntryFile.fromBuffer(buffInFile);
				
				float cost = D[0][buffEntryFile.first] + buffEntryFile.cost;
				if (best == null || cost < best.cost) {
					best = new Entry(null, 0, cost, 0, buffEntryFile.list);
				}
			}
		}
		catch (EOFException e) {
			// expected
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		print(Arrays.stream(best.list).boxed().collect(Collectors.toList()), best.cost);
	}
	
	

	public static void recurArrangements(List<Integer> current, List<Integer> remaining) {
		float s = computeDist(current);
		
		if (s > bestSolScore) {
			// print(current, s);
			return;
		}
		
		if (remaining.isEmpty()) {
			test(current);
			return;
		}
		
		
		for (int i = 0; i < remaining.size(); i++) {
			int v = remaining.remove((int)i);
			current.add(v);
			recurArrangements(current, remaining);
			current.remove(current.size() - 1);
			remaining.add(i, v);
		}
	}
	
	
	public static long binCoeff(int k, int n) {
		return factorial(n)
				.divide(factorial(k)
						.multiply(factorial(n-k)))
				.longValueExact();
	}
	
	public static BigInteger factorial(int x) {
		BigInteger r = BigInteger.ONE;
		for (int i = 2; i <= x; i++)
			r = r.multiply(BigInteger.valueOf(i));
		return r;
	}
	
	
	public static void test(List<Integer> current) {
		float s = computeDist(current);
		
		if (s < bestSolScore) {
			bestSolScore = s;

			print(current, s);
			
		}
	}
	
	
	static float computeDist(List<Integer> current) {
		if (current.size() < 2)
			return 0;

		float s = 0;
		for (int i = 1; i < current.size(); i++) {
			s += D[current.get(i - 1)][current.get(i)];
		}
		s += D[current.get(current.size() - 1)][current.get(0)];
		
		return s;
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
