import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Input2 {
	
	// public data from input
	int rows, cols;
	int n;
	
	Query[] queries;
	
	int[][] matrix;
	
	public Input2(InputStream is) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(is);

		rows = in.nextInt();
		cols = in.nextInt();
		n = in.nextInt();
		
		
		queries = new Query[n];
		
		for (int i = 0; i < n; i++) {
			queries[i] = new Query(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
		}
		
	}
	
	
	
	
	List<Point> getIntersectedCells(Query q) {
		List<Point> points = new ArrayList<>();

		points.add(new Point(q.oX, q.oY));

		//boolean left = q.dX < 0;
		boolean right = q.dX > 0;
		//boolean top = q.dY < 0;
		boolean bottom = q.dY > 0;
		
		System.err.println("q");
		
		if (Math.abs(q.dX) >= Math.abs(q.dY)) {
			float a = q.dY / (float) q.dX;
			float b = q.oY - a * q.oX;
			
			for (int x = q.oX, y = q.oY; inXRange(x); x += (right ? 1 : -1)) {
				//System.err.println("x y " + x + " " + y);
				int nextX = x + (right ? 1 : -1);
				int nextY = y + (bottom ? 1 : -1);
				float yR = a * (x + (right ? .5f : -.5f)) + b;
				//System.err.println("yR " + yR);
				if (yR - Math.floor(yR) > 0.4999 && yR - Math.floor(yR) < 0.5001) {
					if (inXRange(nextX))
						points.add(new Point(nextX, y));
					if (inYRange(nextY))
						points.add(new Point(x, nextY));
					if (inXRange(nextX) && inYRange(nextY))
						points.add(new Point(nextX, nextY));
					y = nextY;
					continue;
				}
				int newY = Math.round(yR);
				if (!inYRange(newY))
					break;
				if (newY != y) {
					points.add(new Point(x, newY));
				}
				if (inXRange(nextX))
					points.add(new Point(nextX, newY));
				y = newY;
			}
			
		}
		else {
			float a = q.dX / (float) q.dY;
			float b = q.oX - a * q.oY;
			// x = a y + b
			
			for (int y = q.oY, x = q.oX; inYRange(y); y += (bottom ? 1 : -1)) {
				//System.err.println("x y " + x + " " + y);
				int nextX = x + (right ? 1 : -1);
				int nextY = y + (bottom ? 1 : -1);
				float xR = a * (y + (bottom ? .5f : -.5f)) + b;
				//System.err.println("xR " + xR);
				if (xR - Math.floor(xR) > 0.4999 && xR - Math.floor(xR) < 0.5001) {
					if (inYRange(nextY))
						points.add(new Point(x, nextY));
					if (inXRange(nextX))
						points.add(new Point(nextX, y));
					if (inXRange(nextX) && inYRange(nextY))
						points.add(new Point(nextX, nextY));
					x = nextX;
					continue;
				}
				int newX = Math.round(xR);
				if (!inXRange(newX))
					break;
				if (newX != x) {
					points.add(new Point(newX, y));
				}
				if (inYRange(nextY))
					points.add(new Point(newX, nextY));
				x = newX;
			}
			
		}
		
		
		
		
		return points;
	}
	
	

	boolean inXRange(int x) { return x >= 0 && x < cols; }
	boolean inYRange(int y) { return y >= 0 && y < rows; }
	
	
	
	boolean intersect(int x, int y, Query q) {
		if (q.dX == 0) { // line goes up or down
			if (x != q.oX)
				return false;
			if (q.dY > 0 && y >= q.oY)
				return true;
			if (q.dY < 0 && y <= q.oY)
				return true;
			return false;
		}
		else if (q.dX > 0) {
			if (x < q.oX)
				return false;
			float a = q.dY / (float) q.dX;
			float b = q.oY - a * q.oX;
			float y1 = a * (x - 0.5f) + b;
			float y2 = a * (x + 0.5f) + b;
			return ((y1 <= y + 0.50001 && y1 >= y - 0.50001)
					|| (y2 <= y + 0.50001 && y2 >= y - 0.50001));
		}
		else {
			if (x > q.oX)
				return false;
			float a = q.dY / (float) q.dX;
			float b = q.oY - a * q.oX;
			float y1 = a * (x - 0.5f) + b;
			float y2 = a * (x + 0.5f) + b;
			return ((y1 <= y + 0.50001 && y1 >= y - 0.50001)
					|| (y2 <= y + 0.50001 && y2 >= y - 0.50001));
		}
	}
	
	
	
	
	class Query {
		int oX, oY, dX, dY;
		public Query(int ox, int oy, int dx, int dy) {
			oX = ox;
			oY = oy;
			dX = dx;
			dY = dy;
		}
	}
	
	
	
	String toString(int[][] matrix) {
		return Arrays.stream(matrix).map(row -> Arrays.toString(row)).collect(Collectors.joining("\n"));
	}
}




