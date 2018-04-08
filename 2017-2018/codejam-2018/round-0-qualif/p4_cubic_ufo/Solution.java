package p4_cubic_ufo;

import java.util.Scanner;

public class Solution {
	
	public static final double SQRT_2 = Math.sqrt(2);
	
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			
			int t = s.nextInt();
			for (int i = 0; i < t; i++) {
				double a = Double.parseDouble(s.next());
				
				double xAngle = 0;
				double zAngle = 0;
				
				if (a < SQRT_2) {
					xAngle = areaToAngleX(a);
				}
				else {
					xAngle = Math.PI/4;
					zAngle = areaToAngleZ(a);
				}
				
				// x from left to right
				// y from bottom to top
				// z from back to forward
				// p1 is back
				// p2 is top
				// p3 is right
				double p1x = 0, p1y = 0, p1z = -0.5, p2x = 0, p2y = 0.5, p2z = 0, p3x = 0.5, p3y = 0, p3z = 0;
				// rotate on x
				if (xAngle > 0) {
					double cosXAngle = Math.cos(xAngle);
					double sinXAngle = Math.sin(xAngle);
					p1y = sinXAngle / 2;
					p1z = - cosXAngle / 2;
					p2y = cosXAngle / 2;
					p2z = sinXAngle / 2;
				}
				// rotate on z
				if (zAngle > 0) {
					double p1ZMag = p1y;
					double p2ZMag = p2y;
					double p3ZMag = p3x;
					
					double cosZAngle = Math.cos(zAngle);
					double sinZAngle = Math.sin(zAngle);
					
					p1x = - sinZAngle * p1ZMag;
					p1y = cosZAngle * p1ZMag;
					p2x = - sinZAngle * p2ZMag;
					p2y = cosZAngle * p2ZMag;
					p3x = cosZAngle * p3ZMag;
					p3y = sinZAngle * p3ZMag;
					
				}
				
				System.out.println("Case #" + (i + 1) + ":");
				System.out.println(p1x + " " + p1y + " " + p1z);
				System.out.println(p2x + " " + p2y + " " + p2z);
				System.out.println(p3x + " " + p3y + " " + p3z);
			}
		}
	}
	
	
	public static double areaToAngleX(double area) {
		// area = Math.sin(aX) + Math.cos(aX)
		// area = Math.cos(Math.PI/4 - aX) * SQRT_2
		// area / SQRT_2 = Math.cos(Math.PI/4 - aX)
		// Math.acos(area / SQRT_2) = Math.PI/4 - aX
		// Math.acos(area / SQRT_2) + aX = Math.PI/4
		// aX = Math.PI/4 - Math.acos(area / SQRT_2)
		return Math.PI/4 - Math.acos(area / SQRT_2);
	}
	
	public static double areaToAngleZ(double area) {
		// area = (Math.cos(aZ) + (Math.sin(aZ) * (SQRT_2 / 2))) * SQRT_2
		// area = SQRT_2 * Math.cos(aZ) + Math.sin(aZ)
		// aZ = Math.asin(area / Math.sqrt(3)) - Math.atan(SQRT_2) // found using solver
		return Math.asin(area / Math.sqrt(3)) - Math.atan(SQRT_2);
	}
	
	
}
