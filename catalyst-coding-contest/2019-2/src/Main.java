import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

public class Main {
	
	static int tick;
	static int[][] covering;
	
	public static void main(String[] args) throws Exception {
		
		File dir = new File("level5");
		
		new File(dir.getAbsolutePath() + ".out").mkdirs();
		
		for (File f : dir.listFiles()) {
			Input in = new Input(new FileInputStream(f));
			
			try (PrintStream out = new PrintStream(new File(dir.getAbsolutePath() + ".out/" + f.getName() + ".out"))) {
				for (Input.Country country : in.countries.values()) {
					out.println(country.capitalCol + " " + country.capitalRow);
				}
			}
			
			/*Input2 in = new Input2(new FileInputStream(f));
			
			try (PrintStream out = new PrintStream(new File(dir.getAbsolutePath() + ".out/" + f.getName() + ".out"))) {
				
				for (Input2.Query q : in.queries) {
					List<Point> pts = in.getIntersectedCells(q);
					

					float a = q.dY / (float) q.dX;
					float b = q.oY - a * q.oX;

					System.err.println("origin = " + q.oX + ";" + q.oY);
					System.err.println("y= - (" + a + ") * x - (" + b + ")");

					out.println(pts.stream().map(p -> p.x + " " + p.y).collect(Collectors.joining(" ")));
				}
			}*/
			
			
		}
		
	}
	
	
	
}
