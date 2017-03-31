import java.awt.Point;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputL2 {
	
	
	
	Map<String, Point> points = new HashMap<>();
	
	
	String ville1d, ville2a;
	
	String ville1h, ville2h;
	
	
	
	public InputL2(InputStream in) {
		Scanner s = new Scanner(in);
		
		int nb = s.nextInt();
		
		for (int i = 0; i < nb; i++) {
			String name = s.next();
			Point p = new Point(s.nextInt(), s.nextInt());
			points.put(name, p);
		}
		
		ville1h = s.next();
		ville2h = s.next();
	}
	
	
	public double distance() {
		return points.get(ville1h).distance(points.get(ville2h));
	}
	
	
	
	
	
	
	
}
