import java.awt.Point;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputL1 {
	
	
	
	Map<String, Point> points = new HashMap<>();
	
	
	String ville1, ville2;
	
	
	
	public InputL1(InputStream in) {
		Scanner s = new Scanner(in);
		
		int nb = s.nextInt();
		
		for (int i = 0; i < nb; i++) {
			String name = s.next();
			Point p = new Point(s.nextInt(), s.nextInt());
			points.put(name, p);
		}
		
		ville1 = s.next();
		ville2 = s.next();
	}
	
	
	public double distance() {
		return points.get(ville1).distance(points.get(ville2));
	}
	
	
	
	
	
	
	
}
