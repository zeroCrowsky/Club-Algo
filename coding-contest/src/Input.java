import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Input {
	
	
	
	public Map<String, Point> villes = new HashMap<>();
	
	public List<Journey> journeys = new ArrayList<>();
	
	public List<String> hyperloopLine = new ArrayList<>();
	
	public int n, d;
	
	public Input(InputStream in) {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(in);
		
		int nb = s.nextInt();
		
		for (int i = 0; i < nb; i++) {
			String name = s.next();
			Point p = new Point(s.nextInt(), s.nextInt());
			villes.put(name, p);
		}
		
		/*
		 * Multiple journeys
		 */
		nb = s.nextInt();

		for (int i = 0; i < nb; i++) {
			String name1 = s.next(), name2 = s.next();
			int time = s.nextInt();
			journeys.add(new Journey(name1, name2, time));
		}

		/*
		 * One journey
		 */
		/*
		String name1 = s.next(), name2 = s.next();
		journeys.add(new Journey(name1, name2, 0));
		*/
		
		
		n = s.nextInt();
		
		d = s.nextInt();
		
	}
	
	
	
	public Point get(String nom) {
		return villes.get(nom);
	}
	
	
	public double distance(String ville1, String ville2) {
		return get(ville1).distance(get(ville2));
	}
	
	public double timeHyperLoopSimple(String ville1, String ville2) {
		return distance(ville1, ville2) / 250 + 200;
	}
	
	public double tempsVoiture(String ville1, String ville2) {
		return distance(ville1, ville2) / 15;
	}
	
	

	
	public String hyperloopClosestTo(String vStart, List<String> line) {
		
		String closest = line.get(0);
		double distClosest = distance(vStart, closest);
		
		for (int i = 1; i < line.size(); i++) {
			double dist = distance(vStart, line.get(i));
			if (dist < distClosest) {
				distClosest = dist;
				closest = line.get(i);
			}
		}
		
		return closest;
	}
	
	
	
	
	
	
	
	
	
}
