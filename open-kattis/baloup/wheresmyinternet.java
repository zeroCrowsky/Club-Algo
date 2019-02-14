import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class wheresmyinternet {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));

	public static void main(String[] args) {
		int N = in.nextInt(); // nb house
		int M = in.nextInt(); // nb net
		
		boolean[] connected = new boolean[N];
		
		@SuppressWarnings("unchecked")
		List<Integer>[] connections = new List[N];
		
		
		for (int i = 0; i < M; i++) {
			int v1 = in.nextInt() - 1;
			int v2 = in.nextInt() - 1;
			if (connections[v1] == null)
				connections[v1] = new ArrayList<>();
			connections[v1].add(v2);
			if (connections[v2] == null)
				connections[v2] = new ArrayList<>();
			connections[v2].add(v1);
		}
		
		recur(connected, 0, connections);
		
		
		List<Integer> finalList = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			if (!connected[i])
				finalList.add(i + 1);
		}
		
		if (finalList.isEmpty())
			System.out.println("Connected");
		else
			for (int h : finalList)
				System.out.println(h);
		
		
	}
	
	
	static void recur(boolean[] connected, int curr, List<Integer>[] connections) {
		connected[curr] = true;
		
		if (connections[curr] != null)
			for (int dest : connections[curr]) {
				if (connected[dest])
					continue;
				recur(connected, dest, connections);
			}
		
	}

}
