import java.awt.Point;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputL2 {
	
	
	
	Map<String, Point> points = new HashMap<>();
	
	
	String villeStart, villeEnd;
	
	String villeHyp1, villeHpy2;
	
	
	
	public InputL2(InputStream in) {
		Scanner s = new Scanner(in);
		
		int nb = s.nextInt();
		
		for (int i = 0; i < nb; i++) {
			String name = s.next();
			Point p = new Point(s.nextInt(), s.nextInt());
			points.put(name, p);
		}
		
		villeStart = s.next();
		villeEnd = s.next();
		
		villeHyp1 = s.next();
		villeHpy2 = s.next();
	}
	
	
	public double distanceHyperloop() {
		return points.get(villeHyp1).distance(points.get(villeHpy2));
	}
	

	
	public String closestToStart() {
		Point start = points.get(villeStart);
		Point h1 = points.get(villeHyp1);
		Point h2 = points.get(villeHpy2);
		
		return (start.distance(h1) < start.distance(h2)) ? villeHyp1 : villeHpy2;
	}
	
	public String closestToEnd() {
		Point end = points.get(villeEnd);
		Point h1 = points.get(villeHyp1);
		Point h2 = points.get(villeHpy2);
		
		return (end.distance(h1) < end.distance(h2)) ? villeHyp1 : villeHpy2;
	}
	
	
	
	
	
	
	
	
	
}
