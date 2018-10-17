import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class e_treasure_map {
	
	static Scanner in = new Scanner(System.in);
	
	static int N;
	static Node[] nodes;
	
	public static void main(String[] args) {
		N = in.nextInt();
		
		nodes = new Node[N];
		
		for (int i = 0; i < N; i++) {
			nodes[i] = new Node(in.nextInt());
		}
		
		
		for (int i = 0; i < N - 1; i++) {
			int n1 = in.nextInt();
			int n2 = in.nextInt();
			Pont p = new Pont(in.nextInt());
			nodes[n1].connectedPonts.add(p);
			nodes[n2].connectedPonts.add(p);
			p.n1 = nodes[n1];
			p.n2 = nodes[n2];
		}
		
		
		int res = nodes[0].compute(null);
		
		
		System.out.println(res);
	}
	
	
	
	
	
	
	
	
	static class Node {
		int W;
		List<Pont> connectedPonts = new ArrayList<>();
		
		public Node(int w) {
			// TODO Auto-generated constructor stub
			W = w;
		}
		
		
		
		public int compute(Pont parent) {
			int sum = W;
			for (Pont p : connectedPonts) {
				if (p == parent)
					continue;
				sum += p.compute(this);
			}
			return sum;
		}
	}
	
	
	
	static class Pont {
		int S;
		Node n1, n2;
		
		public Pont(int s) {
			// TODO Auto-generated constructor stub
			S =s;
		}
		
		
		public int compute(Node parent) {
			return Math.min(S, (n1 != parent ? n1 : n2).compute(this));
		}
	}
	
	
	
	
	
	
	

}
