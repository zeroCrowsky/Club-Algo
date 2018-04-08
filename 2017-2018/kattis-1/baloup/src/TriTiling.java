import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TriTiling {
	
	static Map<Integer, Integer> a = new HashMap<>();
	
	static {
		a.put(0, 1);
		a.put(1, 0);
		a.put(2, 3);
		a.put(3, 0);
		a.put(4, 11);
		a.put(5, 0);
		a.put(6, 41);
		a.put(7, 0);
		a.put(8, 153);
		a.put(9, 0);
		a.put(10, 571);
		a.put(11, 0);
		a.put(12, 2131);
		a.put(13, 0);
		a.put(14, 7953);
		a.put(15, 0);
		a.put(16, 29681);
		a.put(17, 0);
		a.put(18, 110771);
		a.put(19, 0);
		a.put(20, 413403);
		a.put(21, 0);
		a.put(22, 1542841);
		a.put(23, 0);
		a.put(24, 5757961);
		a.put(25, 0);
		a.put(26, 21489003);
		a.put(27, 0);
		a.put(28, 80198051);
		a.put(29, 0);
		a.put(30, 299303201);
	}

	public static void main2(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			
			for(;;) {
				int n = in.nextInt();
				if (n == -1)
					break;
				
				AtomicInteger value = new AtomicInteger(0);
				
				boolean[][] table = new boolean[n][3];
				
				recursive(value, table, new int[] {0, 0});
				
				System.out.println(value);
				
			}
			
		}
	}
	
	
	public static void main(String[] args) {
try (Scanner in = new Scanner(System.in)) {
			
			for(;;) {
				int n = in.nextInt();
				if (n == -1)
					break;
				
				System.out.println(a.get(n));
			}
			
		}
	}
	
	public static void main3(String[] args) {
		for (int n = 0; n <= 30; n++) {
			AtomicInteger value = new AtomicInteger(0);
			boolean[][] table = new boolean[n][3];
			recursive(value, table, new int[] {0, 0});
			System.out.println("a.put("+n+", "+value+");");
		}
	}
	
	
	
	public static void recursive(AtomicInteger v, boolean[][] table, int[] searchAfter) {
		int[] firstFree = findFirstFree(table, searchAfter);
		if (firstFree == null) {
			v.incrementAndGet();
			return;
		}
		
		table[firstFree[0]][firstFree[1]] = true;
		// test vertical
		if (firstFree[0]+1 < table.length
				&& !table[firstFree[0]+1][firstFree[1]]) {
			table[firstFree[0]+1][firstFree[1]] = true;
			recursive(v, table, firstFree);
			table[firstFree[0]+1][firstFree[1]] = false;
		}
		if (firstFree[1]+1 < table[0].length
				&& !table[firstFree[0]][firstFree[1]+1]) {
			table[firstFree[0]][firstFree[1]+1] = true;
			recursive(v, table, firstFree);
			table[firstFree[0]][firstFree[1]+1] = false;
		}
		table[firstFree[0]][firstFree[1]] = false;
		
	}
	
	
	
	public static int[] findFirstFree(boolean[][] table, int[] after) {
		int j = after[1];
		for (int i = after[0]; i < table.length; i++) {
			for (; j < table[0].length; j++) {
				if (!table[i][j])
					return new int[] {i, j};
			}
			j = 0;
		}
		return null;
	}
	
	
	

}
