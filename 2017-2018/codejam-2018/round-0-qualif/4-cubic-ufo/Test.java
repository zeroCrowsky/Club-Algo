
public class Test {

	public static final double SQRT_2 = Math.sqrt(2);
	
	public static void main(String[] args) {
		for (int aX = 0; aX < 35; aX++) {
			double aRad = aX / (double)180 * Math.PI;
			//double r1 = Math.sin(aRad) + Math.cos(aRad);
			//double r2 = Math.cos(Math.PI/4 - aRad) * SQRT_2;
			//double r1 = (Math.cos(aRad) + (Math.sin(aRad) * (SQRT_2 / 2))) * SQRT_2;
			double area = SQRT_2 * Math.cos(aRad) + Math.sin(aRad);
			double a1 = Math.asin(area / Math.sqrt(3)) - Math.atan(SQRT_2);
			double a2 = (-Math.asin(area / Math.sqrt(3))) - Math.atan(SQRT_2) + Math.PI;
			System.out.println(aRad + " " + a1 + " " + a2);
		}
	}
	
}
