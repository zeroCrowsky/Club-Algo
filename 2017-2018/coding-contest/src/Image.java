import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Image {
	int timestamp;
	int row, col;
	
	boolean asteroide = false;
	
	int[][] data;
	
	
	int minR, maxR, minC, maxC;
	boolean[][] asteroidShape;
	
	public Image(int r, int c) {
		row = r; col = c;
		
		
		minR = r; maxR = 0;
		minC = c; maxC = 0;
		
		data = new int[r][c];
	}
	
	
	public void printAsteroid(PrintStream out) {
		for (int r = 0; r < asteroidShape.length; r++) {
			out.print("[");
			for (int c = 0; c < asteroidShape[0].length; c++) {
				out.print(asteroidShape[r][c] ? "#" : " ");
			}
			out.println("]");
		}
	}
	
	
	
	public boolean equalsShape(Image o) {
		if (!asteroide || !o.asteroide)
			return false;

		if (Arrays.deepEquals(asteroidShape, o.asteroidShape))
			return true;
		

		int shapeR = asteroidShape.length;
		int shapeC = asteroidShape[0].length;

		if (shapeR == o.asteroidShape.length)
			return true;
		if (shapeC == o.asteroidShape[0].length)
			return true;

		boolean[][] asteroidShape90 = new boolean[shapeC][shapeR];
		boolean[][] asteroidShape180 = new boolean[shapeR][shapeC];
		boolean[][] asteroidShape270 = new boolean[shapeC][shapeR];
		boolean[][] asteroidShapeFlipR = new boolean[shapeR][shapeC];
		boolean[][] asteroidShapeFlipC = new boolean[shapeR][shapeC];
		
		for (int r = 0; r < shapeR; r++) {
			for (int c = 0; c < shapeC; c++) {
				asteroidShape90[c][shapeR-1-r] = asteroidShape[r][c];
				asteroidShape180[shapeR-1-r][shapeC-1-c] = asteroidShape[r][c];
				asteroidShape270[shapeC-1-c][r] = asteroidShape[r][c];
				asteroidShapeFlipR[shapeR-1-r][c] = asteroidShape[r][c];
				asteroidShapeFlipC[r][shapeC-1-c] = asteroidShape[r][c];
			}
		}
		if (Arrays.deepEquals(asteroidShape90, o.asteroidShape))
			return true;
		if (Arrays.deepEquals(asteroidShape180, o.asteroidShape))
			return true;
		if (Arrays.deepEquals(asteroidShape270, o.asteroidShape))
			return true;
		if (Arrays.deepEquals(asteroidShapeFlipR, o.asteroidShape))
			return true;
		if (Arrays.deepEquals(asteroidShapeFlipC, o.asteroidShape))
			return true;
		return false;
		
		
	}
	
	enum Rotation {
		NOPE, R_90, R_180, R_270, R_ROW_90, R_COL_90, R_ROW_180, R_COL_180;
		
		public Rotation getDouble() {
			switch(this) {
			case NOPE:
				return NOPE;
			case R_90:
				return R_180;
			case R_180:
				return NOPE;
			case R_270:
				return R_180;
			case R_ROW_90:
				return R_ROW_180;
			case R_COL_90:
				return R_COL_180;
			case R_ROW_180:
				return NOPE;
			case R_COL_180:
				return NOPE;
			}
			return null;
		}
		
		
		
	}
	
	public Set<Rotation> getPossibleRotations(Image o) {
		Set<Rotation> rots = new HashSet<>();
		if (!asteroide || !o.asteroide)
			return rots;

		if (Arrays.deepEquals(asteroidShape, o.asteroidShape)) {
			rots.add(Rotation.NOPE);
		}

		int shapeR = asteroidShape.length;
		int shapeC = asteroidShape[0].length;

		if (shapeR == o.asteroidShape.length)
			rots.add(Rotation.R_ROW_90);
		if (shapeC == o.asteroidShape[0].length)
			rots.add(Rotation.R_COL_90);

		boolean[][] asteroidShape90 = new boolean[shapeC][shapeR];
		boolean[][] asteroidShape180 = new boolean[shapeR][shapeC];
		boolean[][] asteroidShape270 = new boolean[shapeC][shapeR];
		boolean[][] asteroidShapeFlipR = new boolean[shapeR][shapeC];
		boolean[][] asteroidShapeFlipC = new boolean[shapeR][shapeC];
		
		for (int r = 0; r < shapeR; r++) {
			for (int c = 0; c < shapeC; c++) {
				asteroidShape90[c][shapeR-1-r] = asteroidShape[r][c];
				asteroidShape180[shapeR-1-r][shapeC-1-c] = asteroidShape[r][c];
				asteroidShape270[shapeC-1-c][r] = asteroidShape[r][c];
				asteroidShapeFlipR[shapeR-1-r][c] = asteroidShape[r][c];
				asteroidShapeFlipC[r][shapeC-1-c] = asteroidShape[r][c];
			}
		}
		if (Arrays.deepEquals(asteroidShape90, o.asteroidShape))
			rots.add(Rotation.R_90);
		if (Arrays.deepEquals(asteroidShape180, o.asteroidShape))
			rots.add(Rotation.R_180);
		if (Arrays.deepEquals(asteroidShape270, o.asteroidShape))
			rots.add(Rotation.R_270);
		if (Arrays.deepEquals(asteroidShapeFlipR, o.asteroidShape))
			rots.add(Rotation.R_ROW_180);
		if (Arrays.deepEquals(asteroidShapeFlipC, o.asteroidShape))
			rots.add(Rotation.R_COL_180);
		
		return rots;
		
	}
}