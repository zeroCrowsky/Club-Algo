import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class wheresmyinternet {
	static Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
	
	
	static int[] parent;

	public static void main(String[] args) throws IOException {
		PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(java.io.FileDescriptor.out), 1 << 16));
		int N = in.nextInt(); // nb house
		int M = in.nextInt(); // nb link
		
		parent = new int[N + 1];
		for (int i = 1; i <= N; i++)
			parent[i] = i;
		
		for (int i = 0; i < M; i++) {
			parent[findParent(in.nextInt())] = findParent(in.nextInt());
		}
		
		boolean connected = true;
		int parent1 = findParent(1);
		for (int i = 2; i <= N; i++) {
			if (findParent(i) != parent1) {
				connected = false;
				out.println(i);
			}
		}
		
		if (connected)
			out.println("Connected");
		out.close();
	}
	
	
	
	
	static int findParent(int i) {
		return parent[i] == i ? i : (parent[i] = findParent(parent[i])); // update current parent value to gain some time
	}

}
