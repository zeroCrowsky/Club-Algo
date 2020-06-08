import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Input {
	
	// public data from input
	int k;
	Panel[] sp;
	
	int rows, cols;
	int[][] a;
	int[][] c;
	
	long min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, avg;
	
	TreeMap<Integer, Country> countries = new TreeMap<>();
	
	public Input(InputStream is) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(is);
		
		k = in.nextInt();
		
		sp = new Panel[k];
		
		for (int i = 0; i < k; i++) {
			sp[i] = new Panel();
			sp[i].country = in.nextInt();
			sp[i].price = in.nextInt();
		}

		rows = in.nextInt();
		cols = in.nextInt();
		
		System.err.println(rows + " " + cols);
		
		long sum = 0;

		a = new int[rows][cols];
		c = new int[rows][cols];
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				a[row][col] = in.nextInt();
				c[row][col] = in.nextInt();
				sum += a[row][col];
				min = Math.min(min, a[row][col]);
				max = Math.max(max, a[row][col]);
				
				if (!countries.containsKey(c[row][col])) {
					countries.put(c[row][col], new Country(c[row][col]));
				}
			}
		}
		
		avg = sum / (rows * cols);
		
		
		fillBorderCount();
		
		fillCenterMass();
		
		fixCapitalPosition();
		
		// System.err.println(toString(c));
		
		
	}
	
	
	void fillBorderCount() {

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (isBorderCell(row, col))
					countries.get(c[row][col]).borderCount++;
			}
		}
	}
	
	
	void fillCenterMass() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				countries.get(c[row][col]).capitalRow += row;
				countries.get(c[row][col]).capitalCol += col;
				countries.get(c[row][col]).cellCount++;
			}
		}
		
		for (Country capital : countries.values()) {
			capital.capitalRow /= capital.cellCount;
			capital.capitalCol /= capital.cellCount;
		}
	}
	
	
	void fixCapitalPosition() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Country country = countries.get(c[row][col]);
				if (c[country.capitalRow][country.capitalCol] == country.id
						&& !isBorderCell(country.capitalRow, country.capitalCol))
					continue;
				// capital mal placée dans ce pays
				if (isBorderCell(row, col))
					continue;

				int distR = row - country.capitalRow;
				int distC = col - country.capitalCol;
				int distSq = distR * distR + distC * distC;
				if (distSq < country.closestToCapitalDistSq) {
					country.closestToCapitalDistSq = distSq;
					country.closestToCapitalRow = row;
					country.closestToCapitalCol = col;
				}
			}
		}
		
		
		for (Country country : countries.values()) {
			if (c[country.capitalRow][country.capitalCol] != country.id
					|| isBorderCell(country.capitalRow, country.capitalCol)) {
				country.capitalRow = country.closestToCapitalRow;
				country.capitalCol = country.closestToCapitalCol;
			}
		}
	}
	
	
	boolean isBorderCell(int row, int col) {
		if (row == 0 || row == rows-1 || col == 0 || col == cols-1)
			return true;
		int country = c[row][col];
		return (c[row-1][col] != country
				|| c[row][col-1] != country
				|| c[row+1][col] != country
				|| c[row][col+1] != country);
	}
	
	
	
	
	
	
	class Country {
		int id;
		int borderCount = 0;
		int cellCount = 0;
		int capitalRow = 0;
		int capitalCol = 0;
		int closestToCapitalRow = Integer.MAX_VALUE;
		int closestToCapitalCol = Integer.MAX_VALUE;
		int closestToCapitalDistSq = Integer.MAX_VALUE;
		public Country(int i) {
			id = i;
		}
	}
	
	
	class Panel {
		int country;
		int price;
	}
	
	
	
	String toString(int[][] matrix) {
		return Arrays.stream(matrix).map(row -> Arrays.toString(row)).collect(Collectors.joining("\n"));
	}
	
	
}




