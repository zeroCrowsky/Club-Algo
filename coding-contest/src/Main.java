import java.awt.Point;

public class Main {
	
	public static void main(String[] args) {
		
		InputL2 in = new InputL2(System.in);

		Point start = in.points.get(in.villeStart);
		Point end = in.points.get(in.villeEnd);

		Point h1 = in.points.get(in.closestToStart());
		Point h2 = in.points.get(in.closestToEnd());
		
		
		
		
		double timeHyperloop = h1.distance(h2) / 250 + 200;
		double before = start.distance(h1) / 15;
		double after = end.distance(h2) / 15;
		
		
		System.out.println(Math.round(timeHyperloop + before + after));
	}
	
}
