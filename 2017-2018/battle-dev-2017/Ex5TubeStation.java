

import java.util.Arrays;
import java.util.Scanner;

/*
 * Exercice 5
 */
public class Ex5TubeStation {
	public static void main( String[] argv ) throws Exception {
		try (Scanner in = new Scanner(System.in)) {
			int n = in.nextInt();
			
			Station[] stations = new Station[n];
			
			Station current = null;
			int minY = 1000, maxY = 0;
			
			for (int i = 0; i < n; i++) {
				stations[i] = new Station(in.nextInt(), in.nextInt(), in.nextInt());
				if (current == null || current.y > stations[i].y) {
					current = stations[i];
				}
				if (stations[i].y < minY)
					minY = stations[i].y;
				if (stations[i].y > maxY)
					maxY = stations[i].y;
			}
			
			Arrays.sort(stations, (s1, s2) -> Integer.compare(s1.y, s2.y));
			
			
			boolean up = true;
			
			double dist = 0;
			
			int count = 0;
			
			while(true) {
				
				Station previous = current;
				if (up) {
					current = nextSupCurrent(stations, current, maxY);
				}
				else {
					current = nextInfCurrent(stations, current, minY);
				}
				
				if (!current.visited)
					count++;
				
				current.visited = true;
				
				
				
				dist += current.dist(previous);
				
				if (count == n) {
					break;
				}
				
				
				
				if (current.y == maxY || current.y == minY) {
					up = !up;
				}
				
				
			}
			
			System.out.println((int) dist);
			
			
			
			
			
		}
	}
	
	

	
	public static Station nextSupCurrent(Station[] stations, Station current, int maxY) {
		Station closest = null;
		double distClosest = 0;
		for (Station s : stations) {
			if (s.y <= current.y)
				continue;
			if (s.visited && s.y != maxY)
				continue;
			
			double dist = current.dist(s);
			if (closest == null || (dist < distClosest && s.y != maxY)) {
				distClosest = dist;
				closest = s;
			}
		}
		
		return closest;
	}
	
	public static Station nextInfCurrent(Station[] stations, Station current, int minY) {
		Station closest = null;
		double distClosest = 0;
		for (Station s : stations) {
			if (s.y >= current.y)
				continue;
			if (s.visited && s.y != minY)
				continue;
			
			
			double dist = current.dist(s);
			if (closest == null || closest.y == minY || dist < distClosest) {
				distClosest = dist;
				closest = s;
			}
		}
		
		return closest;
	}
	
	
	public static class Station {
		int x, y, z;
		boolean visited = false;
		
		public Station(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		
		public double dist(Station s) {
			return Math.sqrt((s.x - x) * (s.x - x)
					+ (s.y - y) * (s.y - y)
					+ (s.z - z) * (s.z - z));
		}
	}
}